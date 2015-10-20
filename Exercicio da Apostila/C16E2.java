// matrizes
// capitulo 16
// exercicio 2 e 3

class somaProduto
{
  void somaMatriz(double[][]a, double[][]b, double[][]c)
  {
    for(int i = 0; i < a.length; i++)
    {
      for(int j = 0;j < a[0].length; j++)
      {
        c[i][j] = a[i][j] + b [i][j];
      }
    }
    for(int i = 0; i < a.length; i++)
    {
      for(int j = 0;j < a[0].length; j++)
      {
        System.out.println("c[" + i + "][" + j + "] = " + c[i][j]);
      }
    }
  }
  void multiplicaMatriz (double[][]a, double[][]b, double[][]c)
  {
    for(int linA = 0; linA < a.length; linA++)
    {
      for(int colB = 0; colB < b[0].length; colB++)
      {
        for(int colAlinB = 0; colAlinB < a[0].length; colAlinB++)
        {
          c[linA][colB] += (a[linA][colAlinB]*b[colAlinB][colB]);  
        }
      }
    }
    for(int linA = 0; linA < a.length; linA++)
    {
      for(int colB = 0; colB < b[0].length; colB++)
      {
        System.out.println("c[" + linA + "][" + colB + "] = " +c[linA][colB]);
      }
    }
  }
  
  
  void exemplo()
  {
  double [][]a,b,c,d,e,f;
  a = new double[2][2];
  b = new double[2][2];
  c = new double[2][2];
  d = new double[2][2];
  e = new double[2][1];
  f = new double[2][1];
  
  a[0][0] = 1;
  a[0][1] = 2;
  a[1][0] = 3;
  a[1][1] = 4;
  
  b[0][0] = -2;
  b[0][1] = 1;
  b[1][0] = 3.0/2.0;
  b[1][1] = -1.0/2.0;
  
  e[0][0] = 3;
  e[1][0] = 4;
  
  System.out.println(ehInversa(a,b));
  
  
  }
  
  boolean ehInversa (double[][]a, double[][]b)
  {
    double[][]c = new double[a.length][b[0].length];
    
    for(int linA = 0; linA < a.length; linA++)
    {
      for(int colB = 0; colB < b[0].length; colB++)
      {
        for(int colAlinB = 0; colAlinB < a[0].length; colAlinB++)
        {
          c[linA][colB] += (a[linA][colAlinB]*b[colAlinB][colB]);  
        }
      }
    }
    
    for(int linA = 0; linA < a.length; linA++)
    {
      for(int colB = 0; colB < b[0].length; colB++)
      {
        System.out.println("c[" + linA + "][" + colB + "] = " +c[linA][colB]);
      }
    }
    
    for(int linA = 0; linA < a.length; linA++)
    {
      for(int colB = 0; colB < b[0].length; colB++)
      {
        if (linA == colB && c[linA][colB] != 1)
          return false;
        if (linA != colB && c[linA][colB] != 0)
          return false;
      }
    }
    return true;
  }
}