package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Anotacao {
  private int id;
  private String titulo;
  private LocalDateTime data_hora;
  private String descricao;
  private String cor;
  private byte[] foto;
  
  public Anotacao clonar() {
    Anotacao clone = new Anotacao();
    clone.setId(id);
    clone.setTitulo("(copy)" + titulo);
    clone.setDataHora(data_hora);
    clone.setDescricao(descricao);
    clone.setCor(cor);
    clone.setFoto(foto);

    return clone;
  }

  public int getId() {
    return id;
  }
  
  public String getTitulo() {
    return titulo;
  }
  
  public LocalDateTime getData_hora() {
    return data_hora;
  }
  
  public String getDescricao() {
    return descricao;
  }
  
  public String getCor() {
    return cor;
  }
  
  public byte[] getFoto() {
    return foto;
  }

  public void setId(int id) {
    this.id = id;
  }
  
  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public void setDataHora(LocalDateTime data_hora) {
    this.data_hora = data_hora;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public void setCor(String cor) {

    // cor deve ser uma string HEX.
    // caso a string esteja errada, a cor padrão será branco (#FFFFFF)

    cor = cor.strip();

    if (cor.length() != 7) {
      this.cor = "#FFFFFF";
      return;
    }
    if (cor.charAt(0) != '#') {
      this.cor = "#FFFFFF";
      return;
    }

    Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE); // ignora esses intervalos, e pega qualquer outro caractere
    Matcher match = pattern.matcher(cor.substring(1)); // pula o primeiro caractere

    if (match.find()) { // se achar um caractere além de a-z ou 0-9
      this.cor = "#FFFFFF";
      return;
    }

    this.cor = cor;
  }

  public void setFotoByPath(String caminho) throws IOException {
    File arquivo = new File(caminho);
    FileInputStream fileInputStream = new FileInputStream(arquivo);
    this.foto = fileInputStream.readAllBytes();
    fileInputStream.close();
    // Path path = Paths.get(caminho);
    // this.foto = Files.readAllBytes(path);
  }

  public void setFoto(byte[] foto) {
    this.foto = foto;
  }

  @Override
  public String toString() {
    return "{id="+id+", titulo="+titulo+", data_hora="+data_hora+", descricao="+descricao+", cor="+ cor + ", foto="+foto+"}";
  }
}