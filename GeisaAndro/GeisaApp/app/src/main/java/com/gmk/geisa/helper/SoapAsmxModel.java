package com.gmk.geisa.helper;

/*import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;*/

/**
 * Created by kenjin on 11/7/2015.
 */
/*
public class SoapAsmxModel {


    public ArrayList<mCustomer> getCustomerInternet() {
        ArrayList<mCustomer> customers = new ArrayList<mCustomer>();
        final String SOAP_ACTION = "http://www.webserviceX.NET/GetCitiesByCountry";
        final String METHOD_NAME = "GetCitiesByCountry";
        final String NAMESPACE = "http://www.webserviceX.NET";
        final String URL = "http://www.webservicex.net/globalweather.asmx?wsdl";

        *//*URL:
        L service

        NAMESPACE:
        must have in service page

        METHOD_NAME:
        function in web service

        SOAP_ACTION = NAMESPACE + METHOD_NAME

        *//*
        SoapObject table = null;                        // Contains table of dataset that returned through SoapObject
        SoapObject client = null;                        // Its the client petition to the web service
        SoapObject tableRow = null;                        // Contains row of table
        SoapObject responseBody = null;                    // Contains XML content of dataset
        HttpTransportSE transport = null;            // That call webservice
        SoapSerializationEnvelope sse = null;

        sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        sse.addMapping(NAMESPACE, "SoapAsmxModel", this.getClass());
        //Note if class name isn't "movie" ,you must change
        sse.dotNet = true; // if WebService written .Net is result=true
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        //movie setmovie = new movie();
        try {
            client = new SoapObject(NAMESPACE, METHOD_NAME);
            client.addProperty("CountryName","indonesia");
            sse.setOutputSoapObject(client);
            sse.bodyOut = client;
            Log.d("IPANG",client.toString());
            androidHttpTransport.call(SOAP_ACTION, sse);

            // This step: get file XML
            responseBody = (SoapObject) sse.getResponse();
            // remove information XML,only retrieved results that returned
            responseBody = (SoapObject) responseBody.getProperty(1);
            // get information XMl of tables that is returned
            table = (SoapObject) responseBody.getProperty(0);
            //Get information each row in table,0 is first row
            tableRow = (SoapObject) table.getProperty(0);
            //customers.director = tableRow.getProperty("Director").toString();
            //setmovie.movie_name = tableRow.getProperty("Movie").toString();
            customers.add(new mCustomer(1, tableRow.getProperty("Country").toString(), tableRow.getProperty("City").toString()));
            Log.d("IPANG",tableRow.getProperty("Country").toString()+ " - "+ tableRow.getProperty("City").toString());
            return customers;

        } catch (Exception e) {
            //setmovie.director = e.toString();
            //setmovie.movie_name = e.toString();
            return customers;
        }

    }
}*/
