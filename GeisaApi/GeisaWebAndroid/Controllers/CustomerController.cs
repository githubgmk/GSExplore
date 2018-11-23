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

    [RoutePrefix("api/Customer")]
    public class CustomerController : ApiController
    {
        private DataContext db = new DataContext();
        private spCustomer sp = new spCustomer();
        CultureInfo cultureInd = CultureInfo.GetCultureInfo("id-ID");

        [Route("GetCustomer")]
        public HttpResponseMessage GetCustomer(String salesId)
        {
            // mCustomer item;
            List<mCustomer> items = new List<mCustomer>();
            items = sp.SelectCustomerBySalesId(salesId);
            // items.Add(item);
            IQueryable<mCustomer> iqueryable = items.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, items);

        }

        [Route("GetCustomerClasification")]
        public HttpResponseMessage GetCustomerClasification(String custId)
        {
            // mCustomer item;
            mCustomerClasification items = null;
            items = sp.SelectCustomerClasificationByCustId(custId);
            // items.Add(item);
           // IQueryable<mCustomer> iqueryable = items.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, items);

        }

        [Route("checkNewUpdateCustomer")]
        [HttpGet]
        public HttpResponseMessage checkNewUpdateCustomer(String salesId)
        {
            List<mCustomer> items = new List<mCustomer>();
            items = sp.SelectCustomerNewUpdateBySalesId(salesId);
            IQueryable<mCustomer> iqueryable = items.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, items);

        }


        [Route("updateCustomer")]
        [AcceptVerbs("POST")]
        public HttpResponseMessage updateCustomer(mCustomer data)
        {
            mCustomerJoin result = new mCustomerJoin();
            result = sp.AddNewCustomer(data, data.SalesmanId.ToString(), 0);
            //IQueryable<mCustomer> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);


        }

        [Route("ResetCustLokasi")]
        [AcceptVerbs("GET")]
        public HttpResponseMessage ResetCustLokasi(int CustId,string ModifBy)
        {

            bool result = sp.ResetCustLokasi(CustId,ModifBy);
            //IQueryable<mCustomer> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);


        }

        [Route("ChangeCustSales")]
        [AcceptVerbs("GET")]
        public HttpResponseMessage ChangeCustSales(int CustId,int salesId,int userId, string Pass)
        {
            bool result=false;
            if (Pass.Equals("geisaadmin"))
            {
                 result = sp.ChangeCustSales(CustId, salesId, userId);
                //IQueryable<mCustomer> iqueryable = result.AsQueryable();
                return Request.CreateResponse(HttpStatusCode.OK, result);
            }else
            {
                return Request.CreateResponse(HttpStatusCode.Forbidden);
            }
           
           


        }

        [Route("updateCustomerDownload")]
        [AcceptVerbs("POST", "PUT")]
        public HttpResponseMessage updateCustomerDownload(putCustomerUpdateStatusBindingModel data)
        {
            // mCustomer item;
            bool items = false;
            List<PostResult> result = new List<PostResult>();
            PostResult hsl = new PostResult();
            try
            {


                items = sp.UpdateCustomerDownloadBySalesId(data.salesId, data.customerlist);
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

        [Route("addNewCustomer")]
        [AcceptVerbs("POST", "PUT")]
        public async Task<HttpResponseMessage> addNewCustomer(AddCustomerBindingModel data)
        {
            mCustomerJoin result = new mCustomerJoin();
            result = sp.AddNewCustomer(data.customer, data.salesId, data.GroupCust);
            //IQueryable<mCustomer> iqueryable = result.AsQueryable();

            //send email on new
            if (result != null && result.CustId != null)
            {

                bool hsl = false;
                try
                {
                    String now = DateTime.Now.ToString("yyyy-MM-dd");
                    if (result.CreatedDate.Equals(now))
                    {
                        hsl = await kirimEmail(result);
                    }else
                    {
                        hsl = true;
                    }
                        
                }
                catch (Exception ex)
                {
                    hsl = false;
                }
            }
            return Request.CreateResponse(HttpStatusCode.OK, result);


        }

        [Route("getCustomerById")]
        public HttpResponseMessage getCustomerById(String salesId, String CustId)
        {
            mCustomer result = new mCustomer();
            result = sp.getCustomer(salesId, CustId);
            //IQueryable<mCustomer> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);


        }

        private async Task<bool> kirimEmail(mCustomerJoin data)
        {
            bool hasil = false;
            try
            {
                if (data != null)
                {
                    DateTime osDate = Convert.ToDateTime(data.JoinDate);
                    var message = await EmailTemplate("Noo");
                    var subject = "[INFO NOO] " + data.CustId + " For: " + data.AliasName;
                    message = message.Replace("ViewBag.SubjectMail", cultureInd.TextInfo.ToTitleCase(subject));
                    string superior = "";
                    if (data.salesman.salesAtasan != null)
                        superior = data.salesman.salesAtasan.SalesmanName;
                    message = message.Replace("@ViewBag.Superior", cultureInd.TextInfo.ToTitleCase(superior));
                    message = message.Replace("@ViewBag.OutletId", cultureInd.TextInfo.ToTitleCase(data.CustId));
                    message = message.Replace("@ViewBag.CustName", cultureInd.TextInfo.ToTitleCase(data.CustName + "/" + data.AliasName));
                    message = message.Replace("@ViewBag.CustAddress", cultureInd.TextInfo.ToTitleCase(data.Address));
                    message = message.Replace("@ViewBag.CreatedDate", cultureInd.TextInfo.ToTitleCase(osDate.ToString("dd MMMM yyyy")));
                    string channel = "";
                    if (data.channel != null)
                        channel = data.ChannelId + "-" + data.channel.ChannelName;
                    message = message.Replace("@ViewBag.Channel", cultureInd.TextInfo.ToTitleCase(channel));
                    message = message.Replace("@ViewBag.Pic", cultureInd.TextInfo.ToTitleCase(data.Pic));
                    message = message.Replace("@ViewBag.Jabatan", cultureInd.TextInfo.ToTitleCase(data.PicJabatan));
                    message = message.Replace("@ViewBag.Contact", cultureInd.TextInfo.ToTitleCase(data.Telp + "/" + data.Hp));
                    string dist = "", distaddres = "", salesman = "";
                    if (data.customerAndBranch != null && data.customerAndBranch.Count > 0)
                    {
                        foreach (mCustomerAndBranch cu in data.customerAndBranch)
                        {
                            dist += cu.DistBranchId + "-" + cu.DistBranchName + "\n";
                            distaddres += cu.AreaCode + "-" + cu.AreaName + "-" + cu.Address + "\n";
                        }
                    }

                    message = message.Replace("@ViewBag.Distributor", cultureInd.TextInfo.ToTitleCase(dist));
                    message = message.Replace("@ViewBag.DistAddress", cultureInd.TextInfo.ToTitleCase(distaddres));
                    if (data.salesman != null)
                        salesman = data.salesman.SalesmanName;
                    message = message.Replace("@ViewBag.SalesName", cultureInd.TextInfo.ToTitleCase(salesman));
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
                log.inserLog("kirimEmailComplain", ex.ToString(), data.SalesmanId.ToString());
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


    }
}
