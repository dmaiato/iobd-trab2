package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;
import modelo.Anotacao;

public class AnotacaoDAO {

  private ConexaoPostgreSQL endereco;

  public AnotacaoDAO(String user, String senha) throws SQLException {
    endereco = new ConexaoPostgreSQL(user, senha);
    endereco.getConexao().close();
  }

  public ArrayList<Anotacao> listar(String ordem) throws SQLException {
    // caso tenha erro, a listagem padrão é desc
    if (ordem == null) {
      ordem = "desc";
    } else if (!ordem.equals("asc") && !ordem.equals("desc")) {
      ordem = "desc";
    }

    ArrayList<Anotacao> anotacoes = new ArrayList<>();

    String sql = "";
    if (ordem.equals("asc")) {
      sql = "SELECT * FROM anotacao ORDER BY data_hora asc";
    } else if (ordem.equals("desc")) {
      sql = "SELECT * FROM anotacao ORDER BY data_hora desc";
    }

    Connection conexao = endereco.getConexao();
    PreparedStatement requisicao = conexao.prepareStatement(sql);
    ResultSet rs = requisicao.executeQuery();

    while (rs.next()) {
      Anotacao anotacao = new Anotacao();
      anotacao.setId(rs.getInt("id"));
      anotacao.setTitulo(rs.getString("titulo"));
      anotacao.setDescricao(rs.getString("descricao"));
      anotacao.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
      anotacao.setCor(rs.getString("cor"));
      anotacao.setFoto(rs.getString("foto") != null ? rs.getString("foto").getBytes() : null);  
      anotacoes.add(anotacao);
    }
    requisicao.close();
    conexao.close();

    return anotacoes;
  }

  public Anotacao inserir(Anotacao newAnotacao) throws SQLException {
    String sql = "INSERT INTO anotacao(titulo, data_hora, descricao, cor, foto) VALUES (?,?,?,?,?) RETURNING id";
    Connection conexao = endereco.getConexao();

    PreparedStatement requisicao = conexao.prepareStatement(sql);
    requisicao.setString(1,newAnotacao.getTitulo());
    requisicao.setTimestamp(2, Timestamp.valueOf(newAnotacao.getData_hora()));
    requisicao.setString(3,newAnotacao.getDescricao());
    requisicao.setString(4,newAnotacao.getCor());
    requisicao.setBytes(5,newAnotacao.getFoto());

    ResultSet rs = requisicao.executeQuery();

    if(rs.next()){
      newAnotacao.setId(rs.getInt("id"));
    }
    requisicao.close();
    conexao.close();

    return newAnotacao;
  }

  public boolean deletar(int id) throws SQLException {
    String sql = "DELETE FROM anotacao a where id=?";
    Connection conexao = endereco.getConexao();

    PreparedStatement requisicao = conexao.prepareStatement(sql);
    requisicao.setInt(1,id);
    int linhasAfetadas = requisicao.executeUpdate();

    requisicao.close();
    conexao.close();

    return linhasAfetadas == 1;
  }
  
  public boolean atualizar(Anotacao anotacao) throws SQLException {
    String sql = "UPDATE anotacao SET titulo = ?, data_hora = ?, descricao = ?, cor = ?, foto = ? WHERE id = ?";
    Connection conexao = endereco.getConexao();

    PreparedStatement requisicao = conexao.prepareStatement(sql);
    requisicao.setString(1,anotacao.getTitulo());
    requisicao.setTimestamp(2, Timestamp.valueOf(anotacao.getData_hora()));
    requisicao.setString(3,anotacao.getDescricao());
    requisicao.setString(4,anotacao.getCor());
    requisicao.setBytes(5,anotacao.getFoto());
    requisicao.setInt(6,anotacao.getId());
    int linhasAfetadas = requisicao.executeUpdate();

    requisicao.close();
    conexao.close();

    return linhasAfetadas == 1;
  }

  public Anotacao obter(int id) throws SQLException {
    String sql = "SELECT * FROM anotacao WHERE id = ?";
    Connection conexao = endereco.getConexao();

    PreparedStatement requisicao = conexao.prepareStatement(sql);
    requisicao.setInt(1, id);

    ResultSet rs = requisicao.executeQuery();
    Anotacao anotacao = new Anotacao(); 
    if(rs.next()){
      anotacao.setId(rs.getInt("id"));
      anotacao.setTitulo(rs.getString("titulo"));
      anotacao.setDescricao(rs.getString("descricao"));
      anotacao.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
      anotacao.setCor(rs.getString("cor"));
      anotacao.setFoto(rs.getString("foto") != null ? rs.getString("foto").getBytes() : null);  
    }
    requisicao.close();
    conexao.close();

    return anotacao;
  }
}