// Capitulo 8
// Exerc�cio 4

class Fibonacci
{
  void imprimeN�merosDeFibonacci (int quantidade)
  {
    int x = 1, FAntes = 0, FAtual =1;
    System.out.println("1");
    while (x < quantidade)
    {
      FAtual = FAtual + FAntes;
      FAntes = FAtual - FAntes;
      System.out.println(FAtual);
      x = x + 1;
    }
  }
}

    
    