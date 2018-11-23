using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.ProsesDB
{
    public class spLog
    {
        internal bool inserLog(String lokasi,String  data,String salesId)
        {
            bool hasil = false;
            try
            {
                string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

                using (SqlConnection con = new SqlConnection(conStr))
                {


                    SqlCommand sqlCmd = new SqlCommand("AndroLogErr", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;

                    sqlCmd.Parameters.AddWithValue("@Lokasi", lokasi);
                    sqlCmd.Parameters.AddWithValue("@Log", data);
                    sqlCmd.Parameters.AddWithValue("@SalesId", salesId);
                    con.Open();
                    int rowInserted = sqlCmd.ExecuteNonQuery();
                    con.Close();
                    hasil = true;
                }
            }
            catch (Exception ex)
            {
                hasil = false;
            }

            return hasil;
        }
    }
}