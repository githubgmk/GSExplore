package com.gmk.geisa.helper;

/**
 * Created by kenjin on 11/8/2015.
 */
public class CallSoap {
    public final String SOAP_ACTION = "http://tempuri.org/Add";

    public  final String OPERATION_NAME = "Add";

    public  final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

    public  final String SOAP_ADDRESS = "http://grasshoppernetwork.com/NewFile.asmx";
    public CallSoap()
    {

    }

    /*public String Call(int a,int b)
    {

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        PropertyInfo pi=new PropertyInfo();
        pi.setName("a");
        pi.setValue(a);
        pi.setType(Integer.class);
        request.addProperty(pi);
        pi=new PropertyInfo();
        pi.setName("b");
        pi.setValue(b);
        pi.setType(Integer.class);
        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
        Object response=null;
        try

        {

            httpTransport.call(SOAP_ACTION, envelope);

            response = envelope.getResponse();



        }

        catch (Exception exception)

        {

            response=exception.toString();

        }


        return response.toString();
    }*/
   /* public ArrayList Call(String a,String b){

    }*/
   public final String SOAP_ACTION1 = "http://ws.cdyne.com/WeatherWS/GetWeatherInformation";

    public  final String OPERATION_NAME1 = "GetWeatherInformation";

    public  final String WSDL_TARGET_NAMESPACE1 = "http://ws.cdyne.com/WeatherWS/";

    public  final String SOAP_ADDRESS1 = "http://wsf.cdyne.com/WeatherWS/Weather.asmx";
  /* public String CallDataSet()
   {

       SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE1,OPERATION_NAME1);
       PropertyInfo pi=new PropertyInfo();
       pi.setName("@CountryName");
       pi.setValue("indonesia");
       pi.setType(String.class);
      // request.addProperty(pi);


       SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
               SoapEnvelope.VER11);
       envelope.dotNet = true;

       envelope.setOutputSoapObject(request);

       HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS1);
       Object response=null;
       try

       {

           httpTransport.call(SOAP_ACTION1, envelope);

           response = envelope.getResponse();



       }

       catch (Exception exception)

       {

           response=exception.toString();

       }


       return response.toString();
   }*/

    public final String SOAP_ACTION2 = "http://www.webserviceX.NET/GetGMTbyCountry";

    public  final String OPERATION_NAME2 = "GetGMTbyCountry";

    public  final String WSDL_TARGET_NAMESPACE2 = "http://www.webserviceX.NET";

    public  final String SOAP_ADDRESS2 = "http://www.webservicex.com/country.asmx";
    /*public String CallDataSet1(String nama)
    {

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE2,OPERATION_NAME2);
        PropertyInfo pi=new PropertyInfo();
        pi.setName("CountryName");
        pi.setValue(nama);
        pi.setType(String.class);
        request.addProperty(pi);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS2);
        httpTransport.debug=true;
        Object response=null;
        try

        {

            httpTransport.call(SOAP_ACTION2, envelope);

           SoapObject resultRequestSOAP = (SoapObject)envelope.bodyIn;
           // SoapObject nameResult = (SoapObject) resultRequestSOAP
           //         .getProperty(0);
            int count = resultRequestSOAP.getPropertyCount();
            String resultString=httpTransport.responseDump;
            Log.d("XML data ", resultString);

            //XmlPullParser doc=
            //doc =XmlPullParser..XMLfromString(resultString);

                    *//*
                     * Retrieve one property from the complex SoapObject
                     * response
                     *//*
           *//* for (int i = 0; i < count - 1; i++) {
                SoapObject simpleSuggestion = (SoapObject) nameResult
                        .getProperty(i);
                stringBuilder.append(simpleSuggestion.getProperty(
                        "Name").toString());
                stringBuilder.append("\n");
            }
            String temp = stringBuilder.toString();*//*

        }

        catch (Exception exception)

        {

            response=exception.toString();

        }


        return response.toString();
    }*/
}
