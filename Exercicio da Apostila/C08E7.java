// Capitulo 8
// Exercício 7

class Euclides
{
  int mdc (int a1, int a2)
  {
    int resto=1;
    while (resto > 0)
    {
      resto = a1 % a2;
      a1 = a2;
      a2 = resto;
    }
    return a1;
  }
}