/*******************************************************************/
/**   MAC 110 - Introdução  a Computação                          **/
/**   IME-USP - Primeiro Semestre de 2011                         **/
/**   2011145 - Fabio Kon                                         **/
/**                                                               **/
/**   Segundo Exercício-Programa -- Gerador de Rintones           **/
/**   Arquivo: Sintetizadotor.java                                **/
/**                                                               **/
/**   Erika Midori Akabane      7557032                           **/
/**   Marcos Kazuya Yamazaki    7577622                           **/
/**                                                               **/
/*******************************************************************/

import java.io.File;
import java.util.*;

class Sintetizador{
  
  static double canalDir, canalEsq, D, B, V, duracaoTot, amplit,
   delta = 0, gamma = 1, modEsq = 1, modDir = 1;
  static int contador = 0;
  static double [] Alfa = new double[4], Beta = new double[4], f = new double[4];
  
  /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
  
  public static void main (String []args){
    Scanner sc = new Scanner(System.in);
    String Nome;
    
    System.out.print("Digite o nome do arquivo de saida (.wav): ");
    Nome = sc.nextLine();
    File saidaWave = new File(Nome);
    Wave wav = new Wave(saidaWave);
    
    System.out.println("Digite os valores de alfa1, beta1, alfa2, beta2, alfa3,..., beta4: ");
    Alfa[0] = sc.nextDouble(); Beta[0] = sc.nextDouble(); Alfa[1] = sc.nextDouble(); Beta[1] = sc.nextDouble();
    Alfa[2] = sc.nextDouble(); Beta[2] = sc.nextDouble(); Alfa[3] = sc.nextDouble(); Beta[3] = sc.nextDouble();
    
    System.out.print("Digite em segundos a duracao total do Ringtone: ");
    duracaoTot = sc.nextDouble();
    
    int numDeAmostras = (int) (44100 * duracaoTot);
    //Calcula o numero de amostra total do ringtone e depois escreve no cabeçalho do arquivo;
    wav.escreveCabecalhoWave (numDeAmostras);
    
    // Recebe os parametros de cada evento sonoro;
    System.out.println("Para cada evento sonoro. Digite, a duracao do evento em segundos, a altura (entre 0 e 120) e o volume (entre 0 e 1): ");
    while(numDeAmostras > 0){ 
      
      D = sc.nextDouble(); B = sc.nextDouble(); V = sc.nextDouble();
      
      calculaAmostras (wav);
      numDeAmostras -= (int)(D*44100);    
    }
    wav.fecha();
    System.out.println("Seu Ringtone foi criado com sucesso! =)");
  }
  
  /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

  static void calculaAmostras (Wave wav){
    
    for(int n = 0; n < D*44100; n++){
      DeltaGamma deltaGamma = new DeltaGamma();
      
      contador = 0; //Serve para orientar o metodo oscilador();
      f[2] = oscilador(B, V, Alfa[2], Beta[2], n, deltaGamma); //Calcula Oscilador 3
      f[3] = oscilador(B, V, Alfa[3], Beta[3], n, deltaGamma); //Calcula Oscilador 4
      f[0] = oscilador(B, V, Alfa[0], Beta[0], n, deltaGamma); //Calcula Oscilador 1
      f[1] = oscilador(B, V, Alfa[1], Beta[1], n, deltaGamma); //Calcula Oscilador 2
      
      /*  Calcula os valores para os canais esquerdo e direito, caso beta 4 seja maior que zero,
      ambos os canais serão iguais.  */
      canalEsq = envoltoria(n, (int)(D*44100)) * (f[0] + f[1] + f[2] + f[3]) * modEsq;
      canalDir = envoltoria(n, (int)(D*44100)) * (f[0] + f[1] + f[2] + f[3]) * modDir;
      
      //Escreve as amostras no arquivo de saida;
      wav.escreveAmostra ((float)canalEsq);
      wav.escreveAmostra ((float)canalDir);
    }
  }
  
  /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
  
  static double oscilador(double altura, double volume, double alfa, double beta, int indiceAmostra, DeltaGamma deltaGamma){
    //A amplitude é a mesma para todo o evento, nao deveria ser calculado para cada amostra !
    //Mas como recebemos o volume como parametro, iremos usa-la =)
    amplit = calculaAmplitude(volume);
    
    if (contador == 0){
      //Como o 'contador' vale zero, é a primeira vez que esse metodo é chamadado
      //portanto ele verificará de o valor de Beta 3 é menor que zero;
      
      if (beta < 0){
        contador++;
        delta = deltaGamma.calculaDelta(indiceAmostra, alfa, beta);
        return 0; //Neste caso o Oscilador 3 vale 0;
      }
    }
    else if (contador == 1){
      //Neste caso é a segunda vez que esse metodo é chamado, portanto sera calculado o Oscilador 4;
      if(beta < 0){
        contador++;
        gamma = deltaGamma.calculaGamma(indiceAmostra, alfa, beta);
        //Calcula os valores dos modificadores, esquerdo e direito, caso o Beta 4 menor que zero;
        modEsq = (1 - Alfa[3]/2 + gamma);
        modDir = (1 - Alfa[3]/2 - gamma);
        return 0; //Neste caso o Oscilador 4 vale 0;
      }
    }
    contador++; 
    // Calcula os Osciladores 3 e 4 caso, beta 3 e 4 sao maiores que zero;
    // Calcula os Osciladores 1 e 2;
    return amplit * alfa * Math.cos(deltaGamma.calculaFrequencia(B + beta) * (2*Math.PI * (indiceAmostra/44100.0) + delta));
  }
  
  /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
  
  static double envoltoria(int indiceAmostra, int numeroDeAmostrasDoEvento){
    if (numeroDeAmostrasDoEvento >= 8820.0){
      //Esse metodo devolve valores entre 0 a 1, que aumentam (ou diminuem)
      //constantemente, dependendo do valor de amostras do evento;
      if (indiceAmostra < 4410.0)
        return indiceAmostra/4410.0;
      else if (indiceAmostra > numeroDeAmostrasDoEvento-4410.0)
        return (numeroDeAmostrasDoEvento-indiceAmostra)/4410.0;
      else //Retorna 1, nas partes intermediárias dos eventos;
        return 1;
    }
    else{ //Caso o evento tenha menos de 8820 amostras;
      if (indiceAmostra < numeroDeAmostrasDoEvento/2.0)
        //Aumenta o valor linearmente de 0 a 1 até a metade do evento;
        return indiceAmostra/(numeroDeAmostrasDoEvento/2.0);
      else
        //Dimunui o valor linearmente de 1 a 0 da metade ao final do evento;
        return (numeroDeAmostrasDoEvento-indiceAmostra)/(numeroDeAmostrasDoEvento/2.0);
    }
  }
 
  /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
  
  static double calculaAmplitude (double vol){
    return ((Math.pow(2, 10*vol - 10)));
  }
}

