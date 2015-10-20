// Capitulo 8
// Exercício 1

class Fatorial
{
  int fatorial = 1;
  int calculaFatorial (int x)
  {
    if (x < 0)
      return -1;
    else
    {
      if (x > 1)
      {
        while (x > 1)
        {
          fatorial = fatorial * x ;
          x = x - 1;
        }
      return fatorial;
      }
      else
      {
        if (x == 1)
          return 1;
        else
          return 0;
      }
    }
  }

  void testaCalculaFatorial()
  {
    Fatorial F = new Fatorial();
    if (F.calculaFatorial (10) == 3628800)
      System.out.println("O programa esta correto para 10!");
    else
      System.out.println("O programa esta danificado para 10!");
    if (F.calculaFatorial (5) == 120)
      System.out.println("O programa esta correto para 5!");
    else
      System.out.println("O programa esta danificado para 5!");
    if (F.calculaFatorial (-5454) == -1)
      System.out.println("O programa esta correto para -5454!");
    else
      System.out.println("O programa esta danificado para -5454!");
    if (F.calculaFatorial (0) == 0)
      System.out.println("O programa esta correto para 0!");
    else
      System.out.println("O programa esta danificado para 0!");
  } 
}

       
        
    