/*******************************************************************/
/**   MAC 110 - Introdução  a Computação                          **/
/**   IME-USP - Primeiro Semestre de 2011                         **/
/**   2011145 - Fabio Kon                                         **/
/**                                                               **/
/**   Segundo Exercício-Programa -- Gerador de Rintones           **/
/**   Arquivo: Wave.java                                          **/
/**                                                               **/
/**   Erika Midori Akabane      7557032                           **/
/**   Marcos Kazuya Yamazaki    7577622                           **/
/**                                                               **/
/*******************************************************************/

import java.io.*;

class Wave {

  /* Parametros relativos `a codificacao de audio */
  final int TAXA_AMOSTRAGEM = 44100;
  final int NUM_BITS = 16;
  final int NUM_CANAIS = 2;
  /* A maior amplitude representavel e' 2^(NUM_BITS-1)-1 */
  final int MAX_AMPLITUDE = 32767;
  /* O deslocamento e' usado para mapear valores negativos
   * no intervalo 2^(NUM_BITS-1)...2^NUM_BITS-1
   */
  final int DESLOCAMENTO = 65536;
  FileOutputStream escritor;
  
  Wave (File saidaWave)  {
    try {
      escritor = new FileOutputStream(saidaWave);
    }
    catch (IOException e) {
      System.err.println("Erro: " + e.getMessage());
    }
  }
  /* Escreve um valor positivo no arquivo de saida usando
   * a codificacao little endian ocupando nbytes.
   */
  void escreveLittleEndian (int valor, int nbytes) 
                           throws IOException
  {  
     for (int i = 0; i < nbytes; i++) {   
        escritor.write((byte)(valor % 256));
        valor = valor/256;
     }
    return;
  }
  
  /* Escreve o cabecalho de um arquivo RIFF/WAVE a partir das
   * informacoes R=taxa de amostragem, B=numero de bits por
   * amostra e C=numero de canais, alem do numero de amostras
   * total.
   */
  void escreveCabecalhoWaveCompleto (int R, int B, int C,
                                    int numeroDeAmostras) 
  {
    int tamBlocoDeAmostras; /* numero total de bytes do bloco de amostras */
    
    try {
    
      /* cada amostra ocupa (B/8) bytes e cada canal possui
      uma amostra independente */
      tamBlocoDeAmostras = numeroDeAmostras * C * (B / 8);
    
      escritor.write("RIFF".getBytes()); /* identificador do bloco RIFF */
    
      escreveLittleEndian(tamBlocoDeAmostras + 36,4); /* tamanho do bloco RIFF */
      escritor.write("WAVEfmt ".getBytes()); /* identificador dos blocos WAVE e de formato */
      escreveLittleEndian( 16, 4); /* tamanho do bloco de formato */
      escreveLittleEndian(1, 2); /* esquema de codificacao das amostras (1=PCM) */
      escreveLittleEndian(C, 2); /* numero de canais */
      escreveLittleEndian(R, 4); /* frequencia de amostragem */
      escreveLittleEndian(R * C * (B / 8), 4); /* numero de bytes por segundo */
      escreveLittleEndian(C * (B / 8), 2); /* numero de bytes por amostra */
      escreveLittleEndian(B, 2); /* numero de bits por amostra */
      escritor.write("data".getBytes()); /* identificador do bloco de amostras */
      escreveLittleEndian(tamBlocoDeAmostras,4); /* num. de bytes das amostras */
     
    }
    catch (IOException e) {
      System.err.println("Erro: " + e.getMessage());
    }
    return;
  }
  
  void escreveCabecalhoWave (int numeroDeAmostras)
  {
    escreveCabecalhoWaveCompleto (TAXA_AMOSTRAGEM, NUM_BITS, NUM_CANAIS, 
                                    numeroDeAmostras);
  }
  
  /* Converte a amostra fracionaria entre -1 e 1 para o 
   * intervalo correspondente `a representacao em 16 bits,
   * arredonda o resultado para um valor inteiro  e escreve
   * no arquivo apontado por saidaWave o codigo correspondente.
   */
  void escreveAmostra (float amostra)
  {
    int iamostra,     /* armazena o valor inteiro da amostra. */
        codigo;       /* codigo escrito por escreveLittleEndian() */
    
    FileOutputStream escritor;
    
    /* converte linearmente para o intervalo -MAX_AMPLITUDE...+MAX_AMPLITUDE */
    amostra = amostra * MAX_AMPLITUDE;
    
    /* corrige valores fora do intervalo */
    if (amostra > MAX_AMPLITUDE)
      amostra = MAX_AMPLITUDE;
    if (amostra < -MAX_AMPLITUDE)
      amostra = -MAX_AMPLITUDE;
    
    /* arredonda valor da amostra */
    iamostra = (amostra >= 0)? (int)(amostra + 0.5) : (int)(amostra - 0.5);
    
    /* a codificacao usa somente valores positivos,
     os valores negativos sao espelhados no intervalo MAX_AMPLITUDE...DESLOCAMENTO */
    codigo = (iamostra >= 0)? iamostra : iamostra + DESLOCAMENTO;
    
    try {
      /* escreve o codigo correspondente usando NUM_BITS/8 bytes/amostra */
      escreveLittleEndian(codigo, NUM_BITS/8);  
      
    }
    catch (IOException e) {
      System.err.println("Erro: " + e.getMessage());
    }
  }
  
  void fecha() {
    try {
      escritor.close();
    }
    catch (IOException e) {
      System.err.println("Erro: " + e.getMessage());
    }
    
  }
}
