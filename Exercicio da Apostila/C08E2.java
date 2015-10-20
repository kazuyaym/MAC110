// Capitulo 8
// Exercicio 2

class Média
{
  double Média;
  int Nnumeros;
  double calculaMédia(int n)
  {
    Nnumeros = n;
    
    while (n > 0)
    { 
      Média = Média + n;
      n = n - 1;
    }
    return Média/Nnumeros;
  }
  
  void testaCalculaMédia ()
  {
    Média M = new Média();
    if (M.calculaMédia(10) == 5.5)
      System.out.println("O seu programa está correto para 10");
    else
      System.out.println("O seu programa esta danificado para 10");
  }
}

      
    
    