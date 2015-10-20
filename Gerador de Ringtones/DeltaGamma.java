/*******************************************************************/
/**   MAC 110 - Introdução  a Computação                          **/
/**   IME-USP - Primeiro Semestre de 2011                         **/
/**   2011145 - Fabio Kon                                         **/
/**                                                               **/
/**   Segundo Exercício-Programa -- Gerador de Rintones           **/
/**   Arquivo: DeltaGamma.java                                    **/
/**                                                               **/
/**   Erika Midori Akabane      7557032                           **/
/**   Marcos Kazuya Yamazaki    7577622                           **/
/**                                                               **/
/*******************************************************************/

class DeltaGamma {
  double calculaDelta(double n, double alfa, double beta) {
    return ((Math.pow(2, alfa/12) - 1) / calculaFrequencia(beta)) * Math.cos(2*Math.PI * calculaFrequencia(beta) * (n/44100.0));
  }

  double calculaGamma(double n, double alfa, double beta) {
    return alfa/2 * Math.cos(2*Math.PI * calculaFrequencia(beta) * (n/44100.0));
  }
  
  double calculaFrequencia (double alt){
    return (27.5 * Math.pow(2, alt/12));
  }
}