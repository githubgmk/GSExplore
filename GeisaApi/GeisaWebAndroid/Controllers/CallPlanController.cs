using GeisaWebAndroid.Models;
using GeisaWebAndroid.Modelsg;
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
using System.Web.UI.WebControls;

namespace GeisaWebAndroid.Controllers
{
    [RoutePrefix("api/CallPlan")]
    public class CallPlanController : ApiController
    {
        private DataContext db = new DataContext();
        private spCallPlan sp = new spCallPlan();
        CultureInfo cultureInd = CultureInfo.GetCultureInfo("id-ID");
        //untuk call plan baru
        [Route("addNewCallPlan")]
        [AcceptVerbs("POST", "PUT")]
        public HttpResponseMessage addNewCallPlan(putCallPlanBindingModel data)
        {
            List<mCallPlan> result = new List<mCallPlan>();
            result = sp.addUpdateCallPlan(data);
            IQueryable<mCallPlan> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);

        }

        [Route("updateCallPlan")]
        [AcceptVerbs("POST")]
        public HttpResponseMessage updateCallPlan(putCallPlanBindingModel data)
        {
            List<mCallPlan> result = new List<mCallPlan>();
            result = sp.UpdateCallPlanStatus(data);
            IQueryable<mCallPlan> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);

        }



        //getallcalpan>=now()
        [Route("getCallPlanByDate")]
        [HttpGet]
        public HttpResponseMessage getCallPlanByDate(string salesId)
        {
            List<mCallPlan> result = new List<mCallPlan>();
            String now = DateTime.Now.ToString("yyyy-MM-dd");
            result = sp.getCallPlanBySalesIdByDate(salesId, now);
            IQueryable<mCallPlan> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);


        }

        [Route("checkNewUpdateCallPlanNoteBetween")]
        [HttpGet]
        public HttpResponseMessage checkNewUpdateCallPlanNoteBetween(string salesId, string dateFrom, string dateTo)
        {
            List<mCallPlanNote> result = new List<mCallPlanNote>();
            result = sp.getCallPlanNoteBySalesIdByDateBetween(salesId, dateFrom, dateTo);
            IQueryable<mCallPlanNote> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);


        }
        [Route("checkNewUpdateCallPlanBetween")]
        [HttpGet]
        public HttpResponseMessage checkNewUpdateCallPlan(string salesId, string dateFrom, string dateTo)
        {
            List<mCallPlan> result = new List<mCallPlan>();
            result = sp.getCallPlanNewUpdateBySalesIdByDateBetween(salesId, dateFrom, dateTo);
            IQueryable<mCallPlan> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);


        }


        [Route("getComplainBetween")]
        [HttpGet]
        public HttpResponseMessage getComplainBetween(string salesId, string dateFrom, string dateTo)
        {
            List<mComplain> result = new List<mComplain>();
            result = sp.getComplainBySalesIdByDateBetween(salesId, dateFrom, dateTo);
            IQueryable<mComplain> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);


        }

        [Route("getDemoBetween")]
        [HttpGet]
        public HttpResponseMessage getDemoBetween(string salesId, string dateFrom, string dateTo)
        {
            List<mCallPlanDemo> result = new List<mCallPlanDemo>();
            result = sp.getDemoBySalesIdByDateBetween(salesId, dateFrom, dateTo);
            IQueryable<mCallPlanDemo> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);


        }

        [Route("getSampleBetween")]
        [HttpGet]
        public HttpResponseMessage getSampleBetween(string salesId, string dateFrom, string dateTo)
        {
            List<mSample> result = new List<mSample>();
            result = sp.getCallPlanSampleBetween(salesId, dateFrom, dateTo);
            IQueryable<mSample> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);


        }
        

        //getcallplan with flag 1
        [Route("checkNewUpdateCallPlan")]
        [HttpGet]
        public HttpResponseMessage checkNewUpdateCallPlan(string salesId)
        {
            List<mCallPlan> result = new List<mCallPlan>();
            String now = DateTime.Now.ToString("yyyy-MM-dd");
            result = sp.getCallPlanNewUpdateBySalesIdByDate(salesId, now);
            IQueryable<mCallPlan> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);


        }

        [Route("updateCallPlanStatus")]
        [AcceptVerbs("POST", "PUT")]
        public HttpResponseMessage updateCallPlanStatus(putStringListBindingModel data)
        {
            // mCustomer item;
            bool items = false;
            List<PostResult> result = new List<PostResult>();
            PostResult hsl = new PostResult();
            try
            {


                items = sp.updateCallPlanStatus(data.salesId, data.StringList);
                hsl.errNot = items;
                hsl.errMsg = "Success";
                // hsl.errTag = data.customerId;               
            }
            catch (Exception ex)
            {
                hsl.errNot = items;
                hsl.errMsg = ex.ToString();
                // hsl.errTag = data.customerId;
            }
            result.Add(hsl);
            IQueryable<PostResult> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);


        }

        //callplan note
        [Route("updateTransCallPlanNote")]
        [HttpPost]
        public async Task<HttpResponseMessage> updateTransCallPlanNote(putCallPlanNoteBindingModel data)
        {
            List<mCallPlanNote> result = new List<mCallPlanNote>();
            foreach (mCallPlanNote cu in data.callplannotelist)
            {
                int items = sp.AddUpdateCallPlanNoteSingle(cu);
                if (items > 0)
                {

                    bool hsl = false;
                    try
                    {
                        mBiAll bi = sp.AndroCallPlanNoteBySalesCallPlanId(cu.CallPlanNoteId, cu.CallPlanId);
                        if (bi != null)
                        {
                            hsl = await kirimEmail(bi);
                        }
                    }
                    catch (Exception ex)
                    {
                        spLog log = new spLog();
                        log.inserLog("ListCallPlanNote", ex.ToString(), data.callplannotelist.ToString()); // 5/10/2018 -- fiqri tambahan ex logerror                     
                        hsl = false;
                    }
                    if (hsl)
                    {
                        result.Add(cu);
                    }
                }
            }


            IQueryable<mCallPlanNote> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);

        }

        private async Task<bool> kirimEmail(mBiAll data)
        {
            bool hasil = false;
            try
            {
                if (data != null)
                {
                    DateTime osDate = Convert.ToDateTime(data.CreatedDate);
                    var message = await EmailTemplate("BiNote");
                    var subject = "[INFO BIN] " + data.CallPlanNoteId + " For: " + data.AliasName;
                    message = message.Replace("ViewBag.SubjectMail", cultureInd.TextInfo.ToTitleCase(subject));
                    string superior = "";
                    if (data.salesman.salesAtasan != null)
                        superior = data.salesman.salesAtasan.SalesmanName;
                    message = message.Replace("@ViewBag.Superior", cultureInd.TextInfo.ToTitleCase(superior));
                    message = message.Replace("@ViewBag.BinId", cultureInd.TextInfo.ToTitleCase(data.CallPlanNoteId));
                    message = message.Replace("@ViewBag.CallPlanId", cultureInd.TextInfo.ToTitleCase(data.CallPlanId));
                    message = message.Replace("@ViewBag.CustName", cultureInd.TextInfo.ToTitleCase(data.CustId + "-" + data.AliasName));
                    message = message.Replace("@ViewBag.CustAddress", cultureInd.TextInfo.ToTitleCase(data.Address));
                    message = message.Replace("@ViewBag.CreatedDate", cultureInd.TextInfo.ToTitleCase(osDate.ToString("dd MMMM yyyy h:mm tt")));
                    message = message.Replace("@ViewBag.BINType", cultureInd.TextInfo.ToTitleCase(data.BiCsTypeName));
                    message = message.Replace("@ViewBag.note1", cultureInd.TextInfo.ToTitleCase(data.Notes1));
                    message = message.Replace("@ViewBag.note2", cultureInd.TextInfo.ToTitleCase(data.Notes2));
                    message = message.Replace("@ViewBag.ProductId", cultureInd.TextInfo.ToTitleCase(data.Notes3));
                    string product = "";
                    if (data.ProductName != null)
                        product = data.ProductName;
                    message = message.Replace("@ViewBag.ProductName", cultureInd.TextInfo.ToTitleCase(product));
                    message = message.Replace("@ViewBag.SalesName", cultureInd.TextInfo.ToTitleCase(data.salesman.SalesmanName));
                    message = message.Replace("@ViewBag.DateSend", cultureInd.TextInfo.ToTitleCase(DateTime.Now.ToString("dd MMMM yyyy h:mm tt")));

                    List<MailAddress> emailTo = new List<MailAddress>();
                    List<MailAddress> emailcc = new List<MailAddress>();
                    if (data.salesman.salesAtasan != null)
                    {
                        //spv lv 8
                        if (data.salesman.salesAtasan.Email != null && !data.salesman.salesAtasan.Email.Equals(""))
                        {
                            foreach (var address in data.salesman.salesAtasan.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                            {
                                emailTo.Add(new MailAddress(address, address));
                            }
                        }
                        //abm lv 7
                        if (data.salesman.salesAtasan.salesAtasan != null)
                        {
                            if (data.salesman.salesAtasan.salesAtasan.Email != null && !data.salesman.salesAtasan.salesAtasan.Email.Equals(""))
                            {
                                foreach (var address in data.salesman.salesAtasan.salesAtasan.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                {
                                    emailTo.Add(new MailAddress(address, address));
                                }
                            }
                            //nbm lv 6
                            if (data.salesman.salesAtasan.salesAtasan.salesAtasan != null)
                            {
                                if (data.salesman.salesAtasan.salesAtasan.salesAtasan.Email != null && !data.salesman.salesAtasan.salesAtasan.salesAtasan.Email.Equals(""))
                                {
                                    foreach (var address in data.salesman.salesAtasan.salesAtasan.salesAtasan.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                    {
                                        emailTo.Add(new MailAddress(address, address));
                                    }
                                }

                            }
                        }


                    }
                    else
                    {
                        emailTo.Add(new MailAddress("system.geisaforce@gandummas.co.id", "Bounch No Address"));
                    }
                    if (data.salesman.Email != null && !data.salesman.Email.Equals(""))
                    {
                        foreach (var address in data.salesman.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                        {
                            emailcc.Add(new MailAddress(address, address));
                        }
                    }
                    await MessageServices.SendEmailAsync("system.geisaforce@gandummas.co.id", "System GeisaForce", emailTo, emailcc, subject, message, null, null);
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
                log.inserLog("kirimEmail", ex.ToString(), data.SalesmanId.ToString());
                hasil = false;
            }

            return hasil;
        }
        public static async Task<string> EmailTemplate(string template)
        {
            var templateFilePath = HostingEnvironment.MapPath("~/Content/templates/") + template + ".cshtml";
            StreamReader objStreamReaderFile = new StreamReader(templateFilePath);
            var body = await objStreamReaderFile.ReadToEndAsync();
            objStreamReaderFile.Close();
            return body;
        }

        //callplan demo
        [Route("updateTransCallPlanDemo")]
        [HttpPost]
        public HttpResponseMessage updateTransCallPlanDemo(putCallPlanDemoBindingModel data)
        {
            List<mCallPlanDemo> result = new List<mCallPlanDemo>();
            foreach (mCallPlanDemo cu in data.callplandemolist)
            {

                int items = sp.AddUpdateCallPlanDemoSingle(cu);
                if (items > 0)
                {
                    result.Add(cu);
                }
            }
            if (result.Count > 0)
            {
                IQueryable<mCallPlanDemo> iqueryable = result.AsQueryable();
                return Request.CreateResponse(HttpStatusCode.OK, result);
            }
            else
            {
                IQueryable<mCallPlanDemo> iqueryable = result.AsQueryable();
                return Request.CreateResponse(HttpStatusCode.BadRequest, result);
            }


        }

        [Route("updateTransCallPlanComplain")]
        [HttpPost]
        public async Task<HttpResponseMessage> updateTransCallPlanComplain(putCallPlanComplainBindingModel data)
        {
            List<mComplain> result = new List<mComplain>();
            foreach (mComplain cu in data.callplancomplainlist)
            {

                int items = sp.AddUpdateCallPlanComplainSingle(cu);
                if (items > 0)
                {
                    //kirim email
                    bool hsl = false;
                    try
                    {
                        mComplainJoin cpln = sp.AndroCallPlanComplainBySalesComplainId(cu.ComplainId, cu.CallPlanId);
                        if (cpln != null)
                        {
                            hsl = await kirimEmailComplain(cpln);
                        }
                    }
                    catch (Exception ex)
                    {
                        hsl = false;
                    }
                    if (hsl)
                    {
                        result.Add(cu);
                    }
                }
            }
            IQueryable<mComplain> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);

        }

        private async Task<bool> kirimEmailComplain(mComplainJoin data)
        {
            bool hasil = false;
            try
            {
                if (data != null)
                {
                    DateTime osDate = Convert.ToDateTime(data.CreatedDate);
                    DateTime ossDate = Convert.ToDateTime(data.SampleSendDate);
                    var message = await EmailTemplate("Complain");
                    var subject = "[INFO COMPLAIN] " + data.ComplainId + " For: " + data.AliasName;
                    message = message.Replace("ViewBag.SubjectMail", cultureInd.TextInfo.ToTitleCase(subject));
                    string superior = "";
                    if (data.salesman.salesAtasan != null)
                        superior = data.salesman.salesAtasan.SalesmanName;
                    message = message.Replace("@ViewBag.Superior", cultureInd.TextInfo.ToTitleCase(superior));
                    // message = message.Replace("@ViewBag.CustName", cultureInd.TextInfo.ToTitleCase(data.CustName));
                    string kategorimasalah = "";
                    if (data.QualityFood)
                    {
                        kategorimasalah += "-Kualitas Produk\n";
                    }
                    if (data.SafetyFood)
                    {
                        kategorimasalah += "-Keamanan Pangan\n";
                    }
                    if (data.QualityApplication)
                    {
                        kategorimasalah += "-Kualitas Aplikasi\n";
                    }
                    if (data.QuantityAll)
                    {
                        kategorimasalah += "-Kuantitas\n";
                    }
                    if (data.PackagingAll)
                    {
                        kategorimasalah += "-Kemasan\n";
                    }
                    message = message.Replace("@ViewBag.ComplainId", cultureInd.TextInfo.ToTitleCase(data.ComplainId));
                    message = message.Replace("@ViewBag.ComplainType", cultureInd.TextInfo.ToTitleCase(kategorimasalah));
                    message = message.Replace("@ViewBag.CustName", cultureInd.TextInfo.ToTitleCase(data.CustId + "-" + data.AliasName +"-"+ data.CustCode));
                    message = message.Replace("@ViewBag.CustAddress", cultureInd.TextInfo.ToTitleCase(data.Address));
                    message = message.Replace("@ViewBag.CreatedDate", cultureInd.TextInfo.ToTitleCase(osDate.ToString("dd MMMM yyyy h:mm tt")));
                    message = message.Replace("@ViewBag.ComplainType", cultureInd.TextInfo.ToTitleCase(kategorimasalah));
                    message = message.Replace("@ViewBag.ProductDetail", cultureInd.TextInfo.ToTitleCase(data.ProductName));
                    message = message.Replace("@ViewBag.ProductId", cultureInd.TextInfo.ToTitleCase(data.ProductId));
                    string product = "";
                    if (data.ProductIdName != null)
                        product = data.ProductIdName;
                    message = message.Replace("@ViewBag.ProductIdName", cultureInd.TextInfo.ToTitleCase(product));
                    message = message.Replace("@ViewBag.SalesName", cultureInd.TextInfo.ToTitleCase(data.salesman.SalesmanName));
                    message = message.Replace("@ViewBag.SampleDate", cultureInd.TextInfo.ToTitleCase(ossDate.ToString("dd MMMM yyyy")));
                    message = message.Replace("@ViewBag.DateSend", cultureInd.TextInfo.ToTitleCase(DateTime.Now.ToString("dd MMMM yyyy h:mm tt")));
                    message = message.Replace("@ViewBag.Pic", cultureInd.TextInfo.ToTitleCase(data.CustPic));
                    message = message.Replace("@ViewBag.Jabatan", cultureInd.TextInfo.ToTitleCase(data.CustPicJabatan));
                    message = message.Replace("@ViewBag.Contact", cultureInd.TextInfo.ToTitleCase(data.CustPicHp));
                    message = message.Replace("@ViewBag.ComplainDetail", cultureInd.TextInfo.ToTitleCase(data.ComplainNote));

                    List<MailAddress> emailTo = new List<MailAddress>();
                    List<MailAddress> emailcc = new List<MailAddress>();
                    if (data.salesman.salesAtasan != null)
                    {
                        //spv lv 8
                        if (data.salesman.salesAtasan.Email != null && !data.salesman.salesAtasan.Email.Equals(""))
                        {
                            foreach (var address in data.salesman.salesAtasan.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                            {
                                emailTo.Add(new MailAddress(address, address));
                            }
                        }
                        //abm lv 7
                        if (data.salesman.salesAtasan.salesAtasan != null)
                        {
                            if (data.salesman.salesAtasan.salesAtasan.Email != null && !data.salesman.salesAtasan.salesAtasan.Email.Equals(""))
                            {
                                foreach (var address in data.salesman.salesAtasan.salesAtasan.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                {
                                    emailTo.Add(new MailAddress(address, address));
                                }
                            }
                            //nbm lv 6
                            if (data.salesman.salesAtasan.salesAtasan.salesAtasan != null)
                            {
                                if (data.salesman.salesAtasan.salesAtasan.salesAtasan.Email != null && !data.salesman.salesAtasan.salesAtasan.salesAtasan.Email.Equals(""))
                                {
                                    foreach (var address in data.salesman.salesAtasan.salesAtasan.salesAtasan.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                    {
                                        emailTo.Add(new MailAddress(address, address));
                                    }
                                }

                            }
                        }


                    }
                    else
                    {
                        emailTo.Add(new MailAddress("system.geisaforce@gandummas.co.id", "Bounch No Address"));
                    }
                    if (data.salesman.Email != null && !data.salesman.Email.Equals(""))
                    {
                        foreach (var address in data.salesman.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                        {
                            emailcc.Add(new MailAddress(address, address));
                        }
                    }
                    await MessageServices.SendEmailAsync("system.geisaforce@gandummas.co.id", "System GeisaForce", emailTo, emailcc, subject, message, null, null);
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
                log.inserLog("kirimEmailComplain", ex.ToString(), data.SalesmanId.ToString());
                hasil = false;
            }

            return hasil;
        }

        [Route("updateTransCallPlanSample")]
        [HttpPost]
        public HttpResponseMessage updateTransCallPlanSample(putCallPlanSampleBindingModel data)
        {
            List<mSample> result = new List<mSample>();
            if (data != null)
            {
                foreach (mSample cu in data.callplansamplelist)
                {

                    int items = sp.AddUpdateCallPlanSampleSingle(cu);
                    if (items > 0)
                    {
                        result.Add(cu);
                    }
                }
            }
            IQueryable<mSample> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);

        }

        [Route("getTransCallPlanSample")]
        [HttpGet]
        public HttpResponseMessage getTransCallPlanSample(String salesId, String custId)
        {
            List<mSample> result = new List<mSample>();
            result = sp.SelectSampleNotFinishSalesCustomerId(salesId, custId);
            IQueryable<mSample> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);

        }


        [Route("GetTodoList")]
        [HttpGet]
        public HttpResponseMessage GetPromo(String salesId, String custId)
        {
            List<mTodoList> items = new List<mTodoList>();
            items = sp.SelectTodoListBySalesCustomerId(salesId, custId);
            IQueryable<mTodoList> iqueryable = items.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, items);

        }

        
        [Route("updateTodoListStatusNew")]
        [AcceptVerbs("POST")]
        public HttpResponseMessage updateTodoListStatusNew(putStringListBindingModel data)
        {
            // mCustomer item;
            bool items = false;
            List<PostResult> result = new List<PostResult>();
            PostResult hsl = new PostResult();
            try
            {


                items = sp.updateTodoListStatusNew(data.salesId, data.StringList);
                hsl.errNot = items;
                hsl.errMsg = "Success";
                // hsl.errTag = data.customerId;               
            }
            catch (Exception ex)
            {
                hsl.errNot = items;
                hsl.errMsg = ex.ToString();
                // hsl.errTag = data.customerId;
            }
            result.Add(hsl);
            IQueryable<PostResult> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);


        }

        [Route("UpdateTodoList")]
        [HttpPost]
        public HttpResponseMessage UpdateTodoList(mTodoList todo)
        {
            mTodoList items = todo;
            bool hasil = sp.UpdateTodoList(todo);
            if (hasil)
            {
                items = todo;
            }
            else
            {
                items = null;
            }
            return Request.CreateResponse(HttpStatusCode.OK, items);


        }




    }
}
