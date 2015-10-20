class Singleton
{
  static boolean instanciaCriada = false;
  static Singleton instancia = null;

  private Singleton()
  {
    instanciaCriada = true;
  }
  
  static Singleton criaIntancia()
  {
    if(instanciaCriada)
    {
      System.out.println("Já foi criada a instancia.");
      return instancia;
    }
    else{
      instancia = new Singleton();
      return instancia;
    }
  }
}

/*-*-*-*-*-*-*-*-*-*/

class Singleton2
{
  static Singleton2 instancia2 = null;
  private Singleton2()
  {
  }
  
  static Singleton2 instancia2()
  {
   if (instancia2 == null)
     instancia2 = new Singleton2();
   return instancia2;
  }
}