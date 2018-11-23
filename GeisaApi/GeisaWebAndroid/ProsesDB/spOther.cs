using GeisaWebAndroid.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.ProsesDB
{

    public class spOther : spLog
    {

        public List<mSurvey> getSurvey(string SalesId, string CustId)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            List<mSurvey> items = new List<mSurvey>();
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {
                    SqlCommand cmd = new SqlCommand("AndroSurveyBySalesId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    cmd.Parameters.AddWithValue("@SalesId", SalesId);
                    cmd.Parameters.AddWithValue("@CustId", CustId);

                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mSurvey item = new mSurvey();
                        item.SurveyId = Int32.Parse(rdr["SurveyId"].ToString());
                        item.SurveyName = rdr["SurveyName"].ToString();
                        item.SurveyDesc = rdr["SurveyDesc"].ToString();
                        item.SurveyType = rdr["SurveyType"].ToString();
                        item.SurveyTypeRef = Int32.Parse(rdr["SurveyTypeRef"].ToString());
                        item.SurveyTypeRefDesc = rdr["SurveyTypeRefDesc"].ToString();
                        item.UsePeriode = Int32.Parse(rdr["UsePeriode"].ToString()) > 0;
                        item.PeriodeStart = DateTime.Parse(rdr["PeriodeStart"].ToString()).ToString("yyyy-MM-dd");
                        item.PeriodeEnd = DateTime.Parse(rdr["PeriodeEnd"].ToString()).ToString("yyyy-MM-dd");
                        item.Status = Int32.Parse(rdr["Status"].ToString()) > 0;
                        mSurveyProperties propertis = new mSurveyProperties();
                        propertis.SurveyId = Int32.Parse(rdr["SurveyId"].ToString());
                        propertis.IntroMessage = rdr["IntroMessage"].ToString();
                        propertis.EndMessage = rdr["EndMessage"].ToString();
                        propertis.SkipIntro = Int32.Parse(rdr["SkipIntro"].ToString()) > 0;
                        item.SurveyProperties = propertis;
                        bool skippersonal = Int32.Parse(rdr["SkipPersonal"].ToString()) > 0;
                        //add personal array
                        mSurveyPersonal personal = new mSurveyPersonal();
                        if (!skippersonal)
                        {
                            personal = getSurveyPersonal(item.SurveyId);
                            personal.SkipPersonal = skippersonal;
                        }
                        else
                        {
                            personal.SkipPersonal = skippersonal;
                        }
                        item.PersonalInformation = personal;
                        //add question array
                        item.Questions = getSurveyQuestion(item.SurveyId);
                        items.Add(item);
                    }
                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("AndroSurveyBySalesId", ex.ToString(), SalesId + "," + CustId);
            }
            return items;
        }

       

        internal bool updateSurvey(mSurveyAnswer survey)
        {
            bool hasil = false; int countfalse = 0;

            if (survey.PersonalInfo != null)
            {
                List<mSurveyAnswer.PersonalValue> personal = survey.PersonalInfo;
                foreach (mSurveyAnswer.PersonalValue data in personal)
                {
                    bool hsl = insertSurveyPersonal(data, survey.SurveyId, survey.AnswerId);
                    if (!hsl)
                    {
                        countfalse += 1;
                    }

                }
            }
            if (survey.SurveyAnswer != null)
            {
                List<mSurveyAnswer.AnswerValue> answer = survey.SurveyAnswer;
                foreach (mSurveyAnswer.AnswerValue data in answer)
                {
                    bool hsl = insertSurveyQuestion(data, survey.SurveyId, survey.AnswerId);
                    if (!hsl)
                    {
                        countfalse += 1;
                    }

                }
            }
            if (countfalse > 0)
            {
                hasil = false;
            }
            else
            {
                hasil = true;
            }
            return hasil;
        }

        public bool insertSurveyQuestion(mSurveyAnswer.AnswerValue answer, String SurveyId, String AnswerId)
        {
            bool hasil = false; int countfalse = 0;

            foreach (mSurveyQuestionaireChoices val in answer.Answer)
            {
                try
                {
                    string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

                    using (SqlConnection con = new SqlConnection(conStr))
                    {

                        SqlCommand sqlCmd = new SqlCommand("AndroSurveyAnswerQuestionInsertUpdate", con);
                        sqlCmd.CommandType = CommandType.StoredProcedure;

                        sqlCmd.Parameters.AddWithValue("@AnswerId", AnswerId);
                        sqlCmd.Parameters.AddWithValue("@SurveyId", SurveyId);
                        sqlCmd.Parameters.AddWithValue("@idAsk", answer.idAsk);
                        sqlCmd.Parameters.AddWithValue("@Ask", answer.Ask);
                        sqlCmd.Parameters.AddWithValue("@TypeAsk", answer.TypeAsk);
                        sqlCmd.Parameters.AddWithValue("@IdChoice", val.IdChoice);
                        sqlCmd.Parameters.AddWithValue("@Selected", val.Selected);
                        sqlCmd.Parameters.AddWithValue("@ValueChoice", val.ValueChoice);

                        con.Open();
                        int rowInserted = sqlCmd.ExecuteNonQuery();
                    }
                }
                catch (Exception ex)
                {
                    String err = ex.ToString();
                    string json = JsonConvert.SerializeObject(val);
                    inserLog("AndroSurveyAnswerQuestionInsertUpdate", err + "\n " + json, AnswerId + "-" + answer.idAsk);
                    countfalse += 1;
                }
            }
            if (countfalse > 0)
            {
                hasil = false;
            }
            else
            {
                hasil = true;
            }

            return hasil;
        }
        public bool insertSurveyPersonal(mSurveyAnswer.PersonalValue personal, String SurveyId, String AnswerId)
        {
            bool hasil = false; int countfalse = 0;

            foreach (mSurveyPersonalValue val in personal.value)
            {
                try
                {
                    string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

                    using (SqlConnection con = new SqlConnection(conStr))
                    {

                        SqlCommand sqlCmd = new SqlCommand("AndroSurveyAnswerPersonalInsertUpdate", con);
                        sqlCmd.CommandType = CommandType.StoredProcedure;

                        sqlCmd.Parameters.AddWithValue("@AnswerId", AnswerId);
                        sqlCmd.Parameters.AddWithValue("@SurveyId", SurveyId);
                        // sqlCmd.Parameters.AddWithValue("@idAsk", personal.idAsk);
                        sqlCmd.Parameters.AddWithValue("@PersonalId", val.PersonalId);
                        sqlCmd.Parameters.AddWithValue("@Title", val.Title);
                        sqlCmd.Parameters.AddWithValue("@Value", val.Value);

                        con.Open();
                        int rowInserted = sqlCmd.ExecuteNonQuery();
                    }
                }
                catch (Exception ex)
                {
                    String err = ex.ToString();
                    string json = JsonConvert.SerializeObject(val);
                    inserLog("AndroSurveyAnswerPersonalInsertUpdate", err + "\n " + json, AnswerId + "-" + val.PersonalId.ToString());
                    countfalse += 1;
                }
            }
            if (countfalse > 0)
            {
                hasil = false;
            }
            else
            {
                hasil = true;
            }

            return hasil;
        }

        public mSurveyPersonal getSurveyPersonal(int SurveyId)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            mSurveyPersonal personal = new mSurveyPersonal();
            List<mSurveyPersonalValue> items = new List<mSurveyPersonalValue>();
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {
                    SqlCommand cmd = new SqlCommand("AndroSurveyPersonalBySurveyId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    cmd.Parameters.AddWithValue("@SurveyId", SurveyId);

                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mSurveyPersonalValue item = new mSurveyPersonalValue();
                        item.PersonalId = Int32.Parse(rdr["PersonalId"].ToString());
                        item.Title = rdr["Title"].ToString();
                        item.Type = rdr["Type"].ToString();
                        item.Required = Int32.Parse(rdr["Required"].ToString()) > 0;
                        items.Add(item);
                    }
                }
                if (items.Count > 0)
                    personal.Value = items;
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("AndroSurveyPersonalBySurveyId", ex.ToString(), SurveyId.ToString());
            }
            return personal;
        }

        public List<mSurveyQuestionaire> getSurveyQuestion(int SurveyId)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            List<mSurveyQuestionaire> items = new List<mSurveyQuestionaire>();
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {
                    SqlCommand cmd = new SqlCommand("AndroSurveyQuestionBySurveyId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    cmd.Parameters.AddWithValue("@SurveyId", SurveyId);

                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mSurveyQuestionaire item = new mSurveyQuestionaire();
                        item.QuestionId = Int32.Parse(rdr["QuestionId"].ToString());
                        item.QuestionTitle = rdr["QuestionTitle"].ToString();
                        item.QuestionType = rdr["QuestionType"].ToString();
                        switch (item.QuestionType.ToLower())
                        {
                            case "checkboxes":
                            case "radioboxes":
                                item.Choices = getSurveyQuestionChoices(item.QuestionId);
                                break;
                        }
                        item.Description = rdr["Description"].ToString();
                        item.Required = Int32.Parse(rdr["Required"].ToString()) > 0;
                        item.RandomChoices = Int32.Parse(rdr["RandomChoices"].ToString()) > 0;
                        item.NumberOfLines = Int32.Parse(rdr["NumberOfLines"].ToString());
                        items.Add(item);
                    }
                }

            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("AndroSurveyQuestionBySurveyId", ex.ToString(), SurveyId.ToString());
            }
            return items;
        }

        public List<mSurveyQuestionaireChoices> getSurveyQuestionChoices(int QuestionId)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            List<mSurveyQuestionaireChoices> items = new List<mSurveyQuestionaireChoices>();
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {
                    SqlCommand cmd = new SqlCommand("AndroSurveyQuestionChoiceByQuestionId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    cmd.Parameters.AddWithValue("@QuestionId", QuestionId);

                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mSurveyQuestionaireChoices item = new mSurveyQuestionaireChoices();
                        item.IdChoice = Int32.Parse(rdr["ChoiceId"].ToString());
                        item.ValueChoice = rdr["ValueChoice"].ToString();
                        items.Add(item);
                    }
                }

            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("AndroSurveyQuestionChoiceByQuestionId", ex.ToString(), QuestionId.ToString());
            }
            return items;
        }



        internal bool updatePesan(mPesan pesan)
        {
            bool hasil = false;


            try
            {
                string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand sqlCmd = new SqlCommand("AndroPesanInsertUpdate", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;

                    //sqlCmd.Parameters.AddWithValue("@id", pesan.id);
                    sqlCmd.Parameters.AddWithValue("@idPengirim", pesan.idPengirim);
                    sqlCmd.Parameters.AddWithValue("@pengirim", pesan.pengirim);
                    sqlCmd.Parameters.AddWithValue("@idPenerima", pesan.idPenerima);
                    sqlCmd.Parameters.AddWithValue("@penerima", pesan.penerima);
                    sqlCmd.Parameters.AddWithValue("@judul", pesan.judul);
                    sqlCmd.Parameters.AddWithValue("@isiPesan", pesan.isiPesan);
                    sqlCmd.Parameters.AddWithValue("@fcmid", pesan.fcmid);
                    sqlCmd.Parameters.AddWithValue("@typePesan", pesan.typePesan);
                    sqlCmd.Parameters.AddWithValue("@dateSend", pesan.dateSend);
                    sqlCmd.Parameters.AddWithValue("@dateRead", pesan.dateRead);
                    sqlCmd.Parameters.AddWithValue("@statusPesan", pesan.statusPesan);
                    sqlCmd.Parameters.AddWithValue("@statusSend", pesan.statusSend ? 1 : 0);
                    sqlCmd.Parameters.AddWithValue("@refId", pesan.refId);

                    con.Open();
                    int rowInserted = sqlCmd.ExecuteNonQuery();
                    hasil = true;
                }
            }
            catch (Exception ex)
            {
                String err = ex.ToString();
                string json = JsonConvert.SerializeObject(pesan);
                inserLog("AndroPesanInsertUpdate", err + "\n " + json, pesan.idPengirim + "-" + pesan.idPenerima);
                hasil = false;
            }


            return hasil;
        }

        internal List<mPesan> getPesan(string salesId)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            List<mPesan> items = new List<mPesan>();
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {
                    SqlCommand cmd = new SqlCommand("AndroPesanBySalesId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    cmd.Parameters.AddWithValue("@SalesId", salesId);

                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mPesan item = new mPesan();
                        item.id = long.Parse(rdr["id"].ToString());
                        item.idPengirim = Int32.Parse(rdr["idPengirim"].ToString());
                        item.pengirim = rdr["pengirim"].ToString();
                        item.idPenerima = Int32.Parse(rdr["idPenerima"].ToString());
                        item.penerima = rdr["penerima"].ToString();
                        item.judul = rdr["judul"].ToString();
                        item.isiPesan = rdr["isiPesan"].ToString();
                        item.fcmid = rdr["fcmid"].ToString();
                        item.typePesan = rdr["typePesan"].ToString();
                        item.dateSend = DateTime.Parse(rdr["dateSend"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        string tglaction = "", tglawal = "";
                        tglawal = Convert.ToDateTime(rdr["dateRead"].ToString()).ToString("yyyy-MM-dd");
                        if (!tglawal.Equals("1900-01-01") && !tglawal.Equals("2000-01-01"))
                            tglaction = Convert.ToDateTime(rdr["dateRead"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.dateRead = tglaction;
                        item.statusPesan = rdr["statusPesan"].ToString();
                        item.statusSend = Int32.Parse(rdr["statusSend"].ToString()) > 0;
                        item.refId = rdr["refId"].ToString();
                        if (!item.statusPesan.Equals(""))
                            items.Add(item);
                        items.AddRange(getPesanByRef(item.refId));
                    }
                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("AndroPesanBySalesId", ex.ToString(), salesId);
            }
            return items;
        }

        internal List<mPesan> getPesanByRef(string refId)
        {
            string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;
            List<mPesan> items = new List<mPesan>();
            try
            {
                using (SqlConnection con = new SqlConnection(conStr))
                {
                    SqlCommand cmd = new SqlCommand("AndroPesanByRefId", con);
                    cmd.CommandType = CommandType.StoredProcedure;

                    cmd.Parameters.AddWithValue("@RefId", refId);

                    con.Open();

                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        mPesan item = new mPesan();
                        item.id = long.Parse(rdr["id"].ToString());
                        item.idPengirim = Int32.Parse(rdr["idPengirim"].ToString());
                        item.pengirim = rdr["pengirim"].ToString();
                        item.idPenerima = Int32.Parse(rdr["idPenerima"].ToString());
                        item.penerima = rdr["penerima"].ToString();
                        item.judul = rdr["judul"].ToString();
                        item.isiPesan = rdr["isiPesan"].ToString();
                        item.fcmid = rdr["fcmid"].ToString();
                        item.typePesan = rdr["typePesan"].ToString();
                        item.dateSend = DateTime.Parse(rdr["dateSend"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        string tglaction = "", tglawal = "";
                        tglawal = Convert.ToDateTime(rdr["dateRead"].ToString()).ToString("yyyy-MM-dd");
                        if (!tglawal.Equals("1900-01-01") && !tglawal.Equals("2000-01-01"))
                            tglaction = Convert.ToDateTime(rdr["dateRead"].ToString()).ToString("yyyy-MM-dd hh:mm:ss");
                        item.dateRead = tglaction;
                        item.statusPesan = rdr["statusPesan"].ToString();
                        item.statusSend = Int32.Parse(rdr["statusSend"].ToString()) > 0;
                        item.refId = rdr["refId"].ToString();
                        if (!item.statusPesan.Equals(""))
                            items.Add(item);
                    }
                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("AndroPesanByRefId", ex.ToString(), refId);
            }
            return items;
        }
        /// <summary>
        /// update suhu
        /// </summary>
        /// <param name="result"></param>
        /// <returns></returns>
        internal bool updateSuhu(mSuhu result)
        {
            bool hasil = false;


            try
            {
                string conStr = ConfigurationManager.ConnectionStrings["DataContext"].ConnectionString;

                using (SqlConnection con = new SqlConnection(conStr))
                {

                    SqlCommand sqlCmd = new SqlCommand("NumpangUpdateSuhu", con);
                    sqlCmd.CommandType = CommandType.StoredProcedure;

                    //sqlCmd.Parameters.AddWithValue("@id", pesan.id);
                    sqlCmd.Parameters.AddWithValue("@machineId", result.machineId);
                    sqlCmd.Parameters.AddWithValue("@dateTaken", result.dateTaken);
                    sqlCmd.Parameters.AddWithValue("@temperaturValue", result.temperaturValue);

                    con.Open();
                    int rowInserted = sqlCmd.ExecuteNonQuery();
                    hasil = true;
                }
            }
            catch (Exception ex)
            {
                String err = ex.ToString();
                string json = JsonConvert.SerializeObject(result);
                inserLog("NumpangUpdateSuhu", err + "\n " + json, result.machineId + "-" + result.dateTaken);
                hasil = false;
            }


            return hasil;
        }
    }
}