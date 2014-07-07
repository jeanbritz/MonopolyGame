class Tariff
{
    private String Code;
    private String Cost;

    Tariff ()
    {
    }



    Tariff (String c,
	    String o)
    {
	Code = c;
	Cost = o;
    }
    
    Tariff(String c)
    {
    Cost =c;
    }


    String getCode ()
    {
	return Code;

    }


    String getCost ()
    {
	return Cost;
    }
}
