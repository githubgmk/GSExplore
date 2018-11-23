using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using GeisaWebAndroid.Models;
using System.Data.SqlClient;
using System.Configuration;
using System.Data;
using Newtonsoft.Json;

namespace GeisaWebAndroid.ProsesDB
{
    public class spTracking
    {
        public bool insertUpdateTrackingBySalesId(mTracking data)
        {
            bool hasil = false;
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {
                    SqlCommand cmd = new SqlCommand("AndroTrackingAddUpdateBySales", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    cmd.Parameters.AddWithValue("@TrackingId", data.TrackingId);
                    cmd.Parameters.AddWithValue("@SalesmanId", Int32.Parse(data.SalesmanId.Trim()));
                    cmd.Parameters.AddWithValue("@TrackingType", data.TrackingType);
                    cmd.Parameters.AddWithValue("@TrackingDate", data.TrackingDate);
                    cmd.Parameters.AddWithValue("@TrackingTime", data.TrackingTime);
                    cmd.Parameters.AddWithValue("@TrackingLat", data.TrackingLat);
                    cmd.Parameters.AddWithValue("@TrackingLot", data.TrackingLot);
                    cmd.Parameters.AddWithValue("@TrackingRef", data.TrackingRef);
                    cmd.Parameters.AddWithValue("@TrackingStatus", data.TrackingStatus);
                    cmd.Parameters.AddWithValue("@CreateDate", data.CreateDate);
                    cmd.Parameters.AddWithValue("@DeviceInfo", data.InfoDevice); 
                    con.Open();
                    var hsl = cmd.ExecuteNonQuery();
                    hasil = true;
                }
                catch (Exception ex)
                {
                    spLog log = new spLog();
                    string json = JsonConvert.SerializeObject(data);
                    log.inserLog("insertUpdateTrackingBySalesId", ex.ToString() + "\n " + json, data.SalesmanId);
                    string err = ex.Message;
                    hasil = false;
                }
                finally
                {
                    con.Close();
                }
            }
            return hasil;
        }

        public bool insertUpdateTrackingPicture(mTrackingPicture data)
        {
            bool hasil = false;
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

            using (SqlConnection con = new SqlConnection(conStr))
            {
                try
                {
                    SqlCommand cmd = new SqlCommand("AndroTrackingPictureAddUpdate", con);
                    cmd.CommandType = CommandType.StoredProcedure;
          
                    cmd.Parameters.AddWithValue("@TrackingPictureId", data.TrackingPictureId);
                    cmd.Parameters.AddWithValue("@PictureRef", data.PictureRef);
                    cmd.Parameters.AddWithValue("@Picture", data.PictureName);
                    cmd.Parameters.AddWithValue("@StatusBattery", data.StatusBattery);
                    cmd.Parameters.AddWithValue("@Note", data.Note);
                    cmd.Parameters.AddWithValue("@CreatedDate", data.CreatedDate);
                    cmd.Parameters.AddWithValue("@CreatedBy", data.CreatedBy);
                    con.Open();
                    var hsl = cmd.ExecuteNonQuery();
                    hasil = true;
                }
                catch (Exception ex)
                {
                    spLog log = new spLog();
                    string json = JsonConvert.SerializeObject(data);
                    log.inserLog("insertUpdateTrackingPicture", ex.ToString() + "\n " + json, data.TrackingPictureId);
                    string err = ex.Message;
                    hasil = false;
                }
                finally
                {
                    con.Close();
                }
            }
            return hasil;
        }
    }
}