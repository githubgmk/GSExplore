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
using System.Web.Http.Description;

namespace GeisaWebAndroid.Controllers
{
    [AllowAnonymous]
    [RoutePrefix("api/Register")]
    public class RegisterController : ApiController
    {
        private DataContext db = new DataContext();
        private spSales sp = new spSales();
        public RegisterController()
        {

        }


        //// POST: api/Sales
        //[ResponseType(typeof(mSales))]
        //public IHttpActionResult PostSales(mSales sales)
        //{
        //    if (!ModelState.IsValid)
        //    {
        //        return BadRequest(ModelState);
        //    }

        //    db.Sales.Add(sales);
        //    db.SaveChanges();

        //    return CreatedAtRoute("DefaultApi", new { id = product.ProductId }, product);
        //}

        // GET: api/Sales
        [Route("GetSales")]
        public HttpResponseMessage GetSales(String uname, String pass)
        {
            mSales item;
            List<mSales> items = new List<mSales>();
            item = sp.SelectSalesByUnamePassword(uname, pass);
            items.Add(item);
            IQueryable<mSales> iqueryable = items.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, items);

        }

        [HttpPost]
        [AllowAnonymous]
        [Route("putSales")]
        public HttpResponseMessage putSales(putSalesBindingModel data)
        {
            mSales item = new mSales();
            List<mSales> items = new List<mSales>();
            if (data != null)
            {
                item = sp.SelectSalesByUnamePassword(data.uname, data.pass);
                if (item != null)
                    items.Add(item);
            }
            IQueryable<mSales> iqueryable = items.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, items);

        }


        [Route("updateSalesDetail")]
        [AcceptVerbs("POST", "PUT")]
        [AllowAnonymous]
        //Need to review again 
        public HttpResponseMessage updateSalesDetail(mSales data)
        {
            mSales item;
            item = sp.UpdateSalesDetail(data.recId.ToString(), data.SalesmanId.ToString(), data.FcmId, data.Imei, data.Email,data.UserName,data.UserPass);
            //items.Add(item);
            return Request.CreateResponse(HttpStatusCode.OK, item);

        }



        [Route("updateSalesLogin")]
        [AcceptVerbs("POST", "PUT")]
        [AllowAnonymous]
        public HttpResponseMessage updateSaleslogin(updateSalesLoginBindingModel data)
        {
            bool item = false;
            List<PostResult> result = new List<PostResult>();
            PostResult hsl = new PostResult();
            try
            {
                item = sp.UpdateSalesLogin(data);
                hsl.errNot = item;
                hsl.errMsg = "Success";
            }
            catch (Exception ex)
            {
                hsl.errNot = item;
                hsl.errMsg = ex.ToString();
            }
            result.Add(hsl);
            IQueryable<PostResult> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);


        }

        [Route("updateSalesLogin")]
        [AcceptVerbs("POST", "PUT")]
        [AllowAnonymous]
        public async Task<HttpResponseMessage> ForgotPassword(string userlogin)
        {
            bool item = false;
            PostResult hsl = new PostResult();
            try
            {
                mSales sales = sp.SelectSalesByUname(userlogin);
                if (sales != null)
                {
                    hsl.errNot = item;
                    hsl.errMsg = "1420";
                    //send email dan generate kodeawait 
                    List<MailAddress> emailTo = new List<MailAddress>();
                    if (sales.Email != null && !sales.Email.Equals(""))
                    {

                        foreach (var address in sales.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                        {
                            emailTo.Add(new MailAddress(address, address));
                        }

                        var message = await EmailTemplate("ResetPassword");
                        message = message.Replace("@ViewBag.Salesman", CultureInfo.CurrentCulture.TextInfo.ToTitleCase(sales.SalesmanName));
                        message = message.Replace("@ViewBag.Value", CultureInfo.CurrentCulture.TextInfo.ToTitleCase(hsl.errMsg));

                        await MessageServices.SendEmailAsync("system.geisaforce@gandummas.co.id", "System GeisaForce", emailTo, null, "[Reset Password] GeisaFOrce", message, null, null);
                    }
                }
                else
                {
                    hsl.errNot = item;
                    hsl.errMsg = "0";
                }

            }
            catch (Exception ex)
            {
                hsl.errNot = item;
                hsl.errMsg = "0";
            }
            // IQueryable<PostResult> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, hsl);


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
