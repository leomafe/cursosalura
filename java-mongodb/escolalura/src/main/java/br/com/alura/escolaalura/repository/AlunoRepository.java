package br.com.alura.escolaalura.repository;

import br.com.alura.escolaalura.codec.AlunoCodec;
import br.com.alura.escolaalura.model.Aluno;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class AlunoRepository {

    private  MongoClient mongoClient;

    private MongoDatabase database;

    private  void criaConexao() {
        Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);
        AlunoCodec alunoCodec = new AlunoCodec(codec);
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(), CodecRegistries.fromCodecs(alunoCodec));

        MongoClientOptions opcoes = MongoClientOptions.builder().codecRegistry(codecRegistry).build();

        this.mongoClient = new MongoClient("localhost:27017", opcoes);
        this.database = mongoClient.getDatabase("test");
    }

    public void salvar(Aluno aluno) {

        criaConexao();
        MongoCollection<Aluno> alunos = this.database.getCollection("alunos", Aluno.class);
        if(aluno.getId() == null) {
            alunos.insertOne(aluno);
        } else {
            alunos.updateOne(Filters.eq("_id", aluno.getId()), new Document("$set", aluno));
        }
        this.mongoClient.close();
    }


    public List<Aluno> obterTodosAlunos() {


        criaConexao();
        MongoCollection<Aluno> alunos = database.getCollection("alunos", Aluno.class);

        MongoCursor<Aluno> resultados = alunos.find().iterator();

        List<Aluno> alunosEncontrados = popularAlunos(resultados);
        mongoClient.close();
        return alunosEncontrados;

    }

    public Aluno obterAlunoPor(String id) {

        criaConexao();
        MongoCollection<Aluno> alunos = this.database.getCollection("alunos", Aluno.class);
        Aluno aluno = alunos.find(Filters.eq("_id", new ObjectId(id))).first();
        mongoClient.close();
        return  aluno;


    }

    public List<Aluno> pesquisarPor(String nome) {

        criaConexao();
        MongoCollection<Aluno> alunosColletion = this.database.getCollection("alunos", Aluno.class);
        MongoCursor<Aluno> resultados = alunosColletion.find(Filters.eq("nome", nome), Aluno.class).iterator();
        List<Aluno> alunos = popularAlunos(resultados);
        fecharConexao();
        return alunos;

    }

    public List<Aluno> pesquisarPor(String classificacao, double nota) {

        criaConexao();

        MongoCollection<Aluno> alunosColletion = this.database.getCollection("alunos", Aluno.class);
        MongoCursor<Aluno> resultados = null;

        if ("reprovados".equals(classificacao)) {
            resultados = alunosColletion.find(Filters.lt("notas", nota)).iterator();
        } else if ("aprovados".equals(classificacao)) {
            resultados = alunosColletion.find(Filters.gte("notas", nota)).iterator();
        }
        List<Aluno> alunos = popularAlunos(resultados);
        fecharConexao();
        return alunos;

    }

    private void fecharConexao() {
        mongoClient.close();
    }

    private List<Aluno> popularAlunos(MongoCursor<Aluno> resultados) {

        List<Aluno> alunos = new ArrayList<>();
        while (resultados.hasNext()) {
            alunos.add(resultados.next());
        }
        return alunos;

    }

    public List<Aluno> pesquisaPorGeolocalizacao(Aluno aluno) {

        criaConexao();
        MongoCollection<Aluno> alunoCollection = this.database.getCollection("alunos", Aluno.class);
        String contato = alunoCollection.createIndex(Indexes.geo2d("contato"));

        List<Double> coordinates = aluno.getContato().getCoordinates();
        Point pontoReferencia = new Point(new Position(coordinates.get(0), coordinates.get(1)));

        MongoCursor<Aluno> resultados = alunoCollection.find(Filters.nearSphere("contato", pontoReferencia, 2000.0, 0.0)).limit(2).skip(1).iterator();
        List<Aluno> alunos = popularAlunos(resultados);
        fecharConexao();
        return alunos;

    }
}
