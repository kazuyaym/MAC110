// Capitulo 8
// Exercicio 2

class M�dia
{
  double M�dia;
  int Nnumeros;
  double calculaM�dia(int n)
  {
    Nnumeros = n;
    
    while (n > 0)
    { 
      M�dia = M�dia + n;
      n = n - 1;
    }
    return M�dia/Nnumeros;
  }
  
  void testaCalculaM�dia ()
  {
    M�dia M = new M�dia();
    if (M.calculaM�dia(10) == 5.5)
      System.out.println("O seu programa est� correto para 10");
    else
      System.out.println("O seu programa esta danificado para 10");
  }
}

      
    
    