/*******************************************************************/
/**   MAC 110 - Introdução  a Computação                          **/
/**   IME-USP - Primeiro Semestre de 2011                         **/
/**   2011145 - Fabio Kon                                         **/
/**                                                               **/
/**   Primeiro Exercício-Programa -- Simulador de Beisebol        **/
/**   Arquivo: SimuladorDeBeisebol.java                           **/
/**                                                               **/
/**   Erika Midori Akabane      7557032                           **/
/**   Marcos Kazuya Yamazaki    7577622                           **/
/**                                                               **/
/*******************************************************************/


import java.util.Random;
import java.util.Scanner;

class SimuladorDeBeisebol
{
  double MediaDeAcertos, ContadorMedia, ContadorDesemp, MelhorDesemp, PiorDesemp;
  Random r = new Random();
  Scanner sn = new Scanner(System.in);
  
  /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
  
  void SimulaJogoBeisebol (double NumDeTemporadas, int OportPorTemp, double Prob)
  {
    double contagem, Recorde = 0, TemporadasRecorde;
    MediaDeAcertos = 0; ContadorMedia = 1; ContadorDesemp = 1; MelhorDesemp = 0; PiorDesemp = 0;
    
    Binomial binomio = new Binomial();
    binomio.CalculaProbabilidade(OportPorTemp, 60, Prob);
    
    for(contagem = 1; contagem <= NumDeTemporadas; contagem++){
      if (Temporada (OportPorTemp, Prob, NumDeTemporadas) == true)
        Recorde++;
      //Contador de recordes, a cada vez que o recorde é batido ou igualado.
    }
    
    System.out.println("O melhor desempenho foi de " + (int)MelhorDesemp + " home runs numa Temporada.");
    System.out.println("O pior desempenho foi de " + (int)PiorDesemp + " home runs numa Temporada.");
    System.out.println("A media de home runs por temporada foi de " + (int)MediaDeAcertos + ".\n");
    System.out.print("O recorde de 60 home runs em uma temporada foi superado ou igualado ");
    System.out.println((int)Recorde + " vezes em " + (int)NumDeTemporadas +" temporadas.");
    
    if (Recorde != 0){ //já que a divisão por zero não é possivel.
      TemporadasRecorde = NumDeTemporadas/Recorde;
      System.out.println("Ou seja uma vez a cada " + (int)TemporadasRecorde + " temporadas.\n");
    }
  }
  
  /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
  
  boolean Temporada (int NumJogadas, double Prob, double NumDeTemporadas)
  {
    int contagem;
    double HomeRun = 0;
        
    for(contagem = 1; contagem <= NumJogadas; contagem++)
    {
      if(AcertoHomeRun (Prob) == true)
        HomeRun = HomeRun + 1;
     /* Conta o numero de HomeRum numa temporada */
    }
    CalculaDesempenho (HomeRun);
    MediaHomeRunPorTemporada (NumDeTemporadas, HomeRun);
    
    /* Devolve true caso o numero de HomeRun dessa temporada
    iguale ou ultrapasse ao recorde. */
    if (HomeRun >= 60)
      return true;
    else
      return false;
  }
  
  /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    
  boolean AcertoHomeRun (double Prob)
  {
    return (r.nextDouble() <= Prob);
    /* Neste método, como o nextDouble da classe Random devolve um numero entre 0.0 e 1.0
    e que a probabilidade é dada entre os mesmos números, podemos considerar que a probabilidade
    de obter um número entre 0.0 a Y, é de Y*100%. Caso isso aconteça, temos um home run realizado com sucesso */
  }
  
  /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
  
  void CalculaDesempenho (double HR)
  {
    /*Se é a primeira vez que esse metodo é chamado, o valor
    HR é atribuido para o melhor e o pior desempenho*/
    if (ContadorDesemp == 1){
      MelhorDesemp = HR;
      PiorDesemp = HR;
      ContadorDesemp = 0;
    }
    /* Caso contrário ele testa com os novos valores de HR */
    else {
      if (HR > MelhorDesemp)
        MelhorDesemp = HR;
      if (HR < PiorDesemp)
        PiorDesemp = HR;
    }
  }
  
  /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
  
  void MediaHomeRunPorTemporada (double NumDeTemporadas, double HomeRun)
  {
    while (ContadorMedia <= NumDeTemporadas){
      /*Enqunto ContMedia é menor que o NumTemporada, o valores de HomeRun
      são apenas somados e armazenado na variavel MediaDeAcertos.*/
      if (ContadorMedia != NumDeTemporadas)
        MediaDeAcertos = MediaDeAcertos + HomeRun;
      /*Quando ContMedia for igual ao NumTemporada, será a última vez
      que esse método será executado, por isso a divisão da soma.*/
      else
        MediaDeAcertos = (MediaDeAcertos + HomeRun)/NumDeTemporadas;
      
      ContadorMedia++;
    }
  }
  
  /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
  
  void exemplo ()
  {
    double NumDeTemporadas;
    System.out.print("Insira o número de temporadas para a simulação: ");
    NumDeTemporadas = sn.nextInt();
    System.out.println("Usando os parâmetros do livro 'O Andar do Bêbado' temos: \n");
    SimulaJogoBeisebol(NumDeTemporadas, 691, 0.06807);
    /*Executa com os valores obtidos atraves o livro com 691 oportunidades, com a probabilidade
    de acertar uma vez a cada 14,7 oportunidades, ou seja 6,8%. */
    
    System.out.println("\n* Para executar o simulador de Beisebol, execute o método SimulaJogoBeisebol(), que recebe do usuário três parametros.");
    System.out.println("O primeiro é o número de temporadas você deseja simular. O segundo é o número de oportunidades que o jogador terá de rebater");
    System.out.println("a bola. E o terceiro é a probabilidade (números entre 0.0 a 1.0) de a cada oportunidade de se fazer um home run.\n\n");
  }
}

/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

class TestaSimuladorDeBeisebol
{
  void TestaAcertoHomeRun ()
  {
    SimuladorDeBeisebol simulador = new SimuladorDeBeisebol();
    
    double contTrue = 0, contagem;
    
    for(contagem = 1; contagem <= 10000; contagem++){
      if (simulador.AcertoHomeRun (1) == true)
        contTrue++;     
    }
    if(contTrue == 10000)
      System.out.println("Seu teste funciona na probabilidade de 100% obtendo 100% de acertos.");
     else
      System.out.println("Ops! Houve um erro no seu programa o esperado era acertar todas!");
    contTrue = 0;
    
    for(contagem = 1; contagem <= 10000; contagem++){
      if (simulador.AcertoHomeRun (0.75) == true)
        contTrue++;     
    }
    if(contTrue >= 7390 && contTrue <= 7610)
      System.out.println("Seu teste funciona na probabilidade de 75% obtendo "+ (contTrue/100) +"% de acertos.");
    else
      System.out.println("Ops! Houve um grande salto no resultado! O esperado era 75% mas foi de " + (contTrue/100) +"%.");
    contTrue = 0;
    
    for(contagem = 1; contagem <= 10000; contagem++){
      if (simulador.AcertoHomeRun (0.5) == true)
        contTrue++;     
    }
    if(contTrue >= 4890 && contTrue <= 5110)
      System.out.println("Seu teste funciona na probabilidade de 50% obtendo "+ (contTrue/100) +"% de acertos.");
     else
      System.out.println("Ops! Houve um grande salto no resultado! O esperado era 50% mas foi de " + (contTrue/100) +"%.");
    contTrue = 0;
    
    for(contagem = 1; contagem <= 10000; contagem++){
      if (simulador.AcertoHomeRun (0.3) == true)
        contTrue++;     
    }
    if(contTrue >= 2890 && contTrue <= 3110)
      System.out.println("Seu teste funciona na probabilidade de 30% obtendo "+ (contTrue/100) +"% de acertos.");
     else
      System.out.println("Ops! Houve um grande salto no resultado! O esperado era 30% mas foi de " + (contTrue/100) +"%.");
    contTrue = 0;
    
    for(contagem = 1; contagem <= 10000; contagem++){
      if (simulador.AcertoHomeRun (0.16) == true)
        contTrue++;     
    }
    if(contTrue >= 1490 && contTrue <= 1710)
      System.out.println("Seu teste funciona na probabilidade de 16% obtendo "+ (contTrue/100) +"% de acertos.");
     else
      System.out.println("Ops! Houve um grande salto no resultado! O esperado era 16% mas foi de " + (contTrue/100) +"%.");
    contTrue = 0;
    
    for(contagem = 1; contagem <= 10000; contagem++){
      if (simulador.AcertoHomeRun (0.01) == true)
        contTrue++;     
    }
    if(contTrue >= 0 && contTrue <= 210)
      System.out.println("Seu teste funciona na probabilidade de 1% obtendo "+ (contTrue/100) +"% de acertos.");
     else
      System.out.println("Ops! Houve um grande salto no resultado! O esperado era 1% mas foi de " + (contTrue/100) +"%.");
    contTrue = 0;
  }
}

/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

class Binomial 
{
  /* Calcula a probabilidade de superar ou igualar ao recorde */
  void CalculaProbabilidade (double NumDeExperimentos,double Sucesso, double ProbSucesso)
  {
    double Combinacao, Potencia1, Potencia2, Probabilidade = 0;

    while (Sucesso <= NumDeExperimentos){
      Combinacao = Combinacao(NumDeExperimentos,Sucesso);
      Potencia1 = Potencia(ProbSucesso,Sucesso);
      Potencia2 =  Potencia(1-ProbSucesso,NumDeExperimentos-Sucesso);
      
      Probabilidade = Probabilidade + Combinacao*Potencia1*Potencia2;
      Sucesso++;
    }
    System.out.println("A probabilidade de igualar ou superar o recorde por temporada é de "+(Probabilidade*100)+"%");
  }
 
  /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
  
  double Combinacao (double n, double k)
  {
    double resultado = 1;
    
    while (k > 0)
    {
      resultado = resultado * (n/k);
      n--;
      k--;
    }
    return resultado;
  }
  
  /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
  
  double Potencia (double n, double e)
    //"e" se refere ao expoente da conta no binômio
  {
    double resultado = 1;
    while (e > 0){
      resultado = resultado * n;
      e--;
    }
    return resultado;
  }
}

/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
