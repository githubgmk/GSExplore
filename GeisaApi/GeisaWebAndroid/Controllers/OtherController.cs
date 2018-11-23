using GeisaWebAndroid.Models;
using GeisaWebAndroid.ProsesDB;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Mail;
using System.Threading.Tasks;
using System.Web.Hosting;
using System.Web.Http;

namespace GeisaWebAndroid.Controllers
{
    [RoutePrefix("api/Other")]
    public class OtherController : ApiController
    {
        CultureInfo cultureInd = CultureInfo.GetCultureInfo("id-ID");
        private spOther sp = new spOther();
        [Route("getSurvey")]
        [HttpGet]
        public HttpResponseMessage getSurvey(string salesId, string custId)
        {
            List<mSurvey> result = new List<mSurvey>();
            result = sp.getSurvey(salesId, custId);
            IQueryable<mSurvey> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);


        }

        [Route("sendSurvey")]
        [HttpPost]
        public HttpResponseMessage sendSurvey(mSurveyAnswer survey)
        {
            mSurveyAnswer result = survey;
            if (result != null)
            {
                bool hasil = false;
                hasil = sp.updateSurvey(result);
                result.StatusSend = hasil;
            }
            return Request.CreateResponse(HttpStatusCode.OK, result);


        }

        [Route("getPesan")]
        [HttpGet]
        public HttpResponseMessage getPesan(string salesId)
        {
            List<mPesan> result = new List<mPesan>();
            result = sp.getPesan(salesId);
            IQueryable<mPesan> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);


        }

        [Route("sendPesan")]
        [HttpPost]
        public HttpResponseMessage sendPesan(mPesan pesan)
        {
            mPesan result = pesan;
            if (result != null)
            {
                bool hasil = false;
                hasil = sp.updatePesan(result);
                result.statusSend = hasil;
            }
            return Request.CreateResponse(HttpStatusCode.OK, result);


        }
        /// <summary>
        /// get suhu from machine
        /// </summary>
        /// <param name="suhu"></param>
        /// <returns></returns>
        [Route("sendSuhu")]
        [HttpPost]
        public async Task<HttpResponseMessage> sendSuhu(mSuhu suhu)
        {
            mSuhu result = suhu;
            if (result != null)
            {
                bool hasil = false;
                hasil = sp.updateSuhu(result);
                result.statusSend = hasil;
                if (result.temperaturValue >= 26 || result.temperaturValue <= 17)
                {
                    //send email
                    hasil = await kirimEmail(result);

                }
            }
            return Request.CreateResponse(HttpStatusCode.OK, result);


        }
        //kirim email
        private async Task<bool> kirimEmail(mSuhu data)
        {
            bool hasil = false;
            try
            {
                if (data != null)
                {
                    DateTime osDate = Convert.ToDateTime(data.dateTaken);
                    var message = await EmailTemplate("ReportSuhu");
                    var subject = "[Warning Suhu] " + data.machineId + " For: " + data.temperaturValue;
                    message = message.Replace("ViewBag.SubjectMail", cultureInd.TextInfo.ToTitleCase(subject));

                    message = message.Replace("@ViewBag.Superior", cultureInd.TextInfo.ToTitleCase("SysAdmin"));
                    message = message.Replace("@ViewBag.MachineId", cultureInd.TextInfo.ToTitleCase(data.machineId));
                    message = message.Replace("@ViewBag.Temperatur", cultureInd.TextInfo.ToTitleCase(data.temperaturValue.ToString()));
                    message = message.Replace("@ViewBag.DateTaken", cultureInd.TextInfo.ToTitleCase(osDate.ToString("dd MMMM yyyy")));
                    string channel = "";
                    if (data.temperaturValue >= 26)
                    {
                        channel = "Diatas batas Normal";
                    }
                    else
                    {
                        channel = "Dibawah batas Normal";
                    }
                    message = message.Replace("@ViewBag.Status", cultureInd.TextInfo.ToTitleCase(channel));
                    message = message.Replace("@ViewBag.DateSend", cultureInd.TextInfo.ToTitleCase(DateTime.Now.ToString("dd MMMM yyyy h:mm tt")));

                    List<MailAddress> emailTo = new List<MailAddress>();
                    List<MailAddress> emailcc = new List<MailAddress>();
                    emailTo.Add(new MailAddress("itmonitoring@gandummas.co.id", "Triyono"));
                    //emailTo.Add(new MailAddress("ahmad.fauzi@gandummas.co.id", "Fauzi"));
                    emailTo.Add(new MailAddress("fiqri.putra@gandummas.co.id", "fiqri"));
                    //emailcc.Add(new MailAddress("isasono@gandummas.co.id", "Ipang S"));
                    //emailcc.Add(new MailAddress("andi@gandummas.co.id", "Andi Permana"));
                    //emailcc.Add(new MailAddress("okta.priadi@gandummas.co.id", "Okta Priadi"));
                    //emailcc.Add(new MailAddress("edysulistyo@gandummas.co.id", "Edy S"));
                    await MessageServices.SendEmailAsync("system.geisaforce@gandummas.co.id", "System Server Monitoring", emailTo, emailcc, subject, message, null, null);
                    hasil = true;
                }
                else
                {
                    hasil = true;
                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("kirimEmailSuhu", ex.ToString(), data.dateTaken.ToString());
                hasil = false;
            }

            return hasil;
        }
        private static async Task<string> EmailTemplate(string template)
        {
            var templateFilePath = HostingEnvironment.MapPath("~/Content/templates/") + template + ".cshtml";
            StreamReader objStreamReaderFile = new StreamReader(templateFilePath);
            var body = await objStreamReaderFile.ReadToEndAsync();
            objStreamReaderFile.Close();
            return body;
        }
    }
}
