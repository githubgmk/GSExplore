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
using System.Net.Mime;
using System.Threading.Tasks;
using System.Web.Hosting;
using System.Web.Http;
using System.Web.UI.WebControls;
using GeisaWebAndroid.Export;
using Newtonsoft.Json;

namespace GeisaWebAndroid.Controllers
{
    [RoutePrefix("api/PO")]
    public class PoController : ApiController
    {
        private DataContext db = new DataContext();
        private spPO sp = new spPO();
        CultureInfo cultureInd = CultureInfo.GetCultureInfo("id-ID");
        [Route("addNewPO")]
        [AcceptVerbs("POST")]
        public HttpResponseMessage addNewPO(mPO data)
        {
            //List<mPO> result = new List<mPO>();
            bool items = sp.insertUpdatePO(data);
            if (items)
            {
                return Request.CreateResponse(HttpStatusCode.OK, data);
            }
            else
            {
                return Request.CreateResponse(HttpStatusCode.InternalServerError, data);
            }

        }

        [Route("checkNewUpdatePo")]
        [HttpGet]
        public HttpResponseMessage checkNewUpdatePo(string salesId, string dateFrom, string dateTo)
        {
            List<mPO> result = new List<mPO>();
            result = sp.getPoNewUpdateBySalesIdBetween(salesId, dateFrom, dateTo);
            IQueryable<mPO> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);


        }

        [Route("updatePoStatus")]
        [AcceptVerbs("POST", "PUT")]
        public HttpResponseMessage updatePoStatus(putStringListBindingModel data)
        {
            // mCustomer item;
            bool items = false;
            List<PostResult> result = new List<PostResult>();
            PostResult hsl = new PostResult();
            try
            {
                items = sp.updatePoStatus(data.salesId, data.StringList);
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


        [Route("salesConfirmPO")]
        [AcceptVerbs("POST")]
        public async Task<HttpResponseMessage> salesConfirmPO(mPO po)
        {
            //List<mPO> result = new List<mPO>();
            bool items = false;
            string attach = null, attachpdf = null;
            List<String> attachment = new List<string>();
            string partialPP = null;
            items = sp.insertUpdatePO(po);
            if (items)
            {

                try
                {
                    mPO data = sp.getPoById(po.PoId);
                    // mPO data = sp.getPoById("2018/01/05-90101102");// ("2017/08/23-90100723");// ("2017 /08/08-73080836");
                    if (data != null)
                    {
                        if (data.PoStatusId == 7)
                        {
                            items = true;
                            //}
                            //else if (data.isSellOut)
                            //{
                            //    items = true;
                        }
                        else
                        {

                            DateTime osDate = Convert.ToDateTime(po.ShipDate);
                            DateTime opDate = Convert.ToDateTime(po.PoDate);
                            DateTime ocoDate = Convert.ToDateTime(po.ConfirmDate);
                            DateTime ocDate = Convert.ToDateTime(po.CreatedDate);
                            DateTime oeDate = Convert.ToDateTime(po.EndPeriodeDate);
                            //send email to client with format po
                            AlternateView altView = null;
                            string namatemplate = "PoReguler";
                            var message = await EmailTemplate(namatemplate);
                            var subject = "[INFO PO] " + data.PoId + " For: ";
                            int custlevel = 0;
                            if (data.isSellOut)
                                subject = "[INFO SELLOUT] " + data.PoId + " For: ";
                            if (data.customer != null)
                            {
                                subject += data.customer.AliasName;
                                custlevel = data.customer.CustLevelId;
                            }

                            if (data.isPP)
                            {
                                ExcelFile excel = new ExcelFile();
                                attach = excel.ExportExcelNew(data, custlevel, partialPP);
                                if (attach != null)
                                {
                                    attachment.Add(attach);
                                }

                                namatemplate = "PoPP";
                                if (custlevel == 1)
                                    namatemplate = "PoPPDirect";

                                subject = "[INFO PP] " + data.PoId + " For: ";
                                if (data.customer != null)
                                    subject += data.customer.AliasName;

                                message = await EmailTemplate(namatemplate);
                                message = message.Replace("@ViewBag.SubjectMail", cultureInd.TextInfo.ToTitleCase(subject));
                                message = message.Replace("@ViewBag.PicDist", cultureInd.TextInfo.ToTitleCase(data.PicDist));
                                message = message.Replace("@ViewBag.Area", cultureInd.TextInfo.ToTitleCase(data.distBranch.AreaCode + "  " + data.distBranch.AreaName));
                                message = message.Replace("@ViewBag.PoId", cultureInd.TextInfo.ToTitleCase(data.PoId));
                                message = message.Replace("@ViewBag.PoRefId", cultureInd.TextInfo.ToTitleCase(data.PoCustNumberRef));
                                message = message.Replace("@ViewBag.PoDate", cultureInd.TextInfo.ToTitleCase(opDate.ToString("dd MMMM yyyy h:mm tt")));
                                message = message.Replace("@ViewBag.SalesConfirmDate", cultureInd.TextInfo.ToTitleCase(ocoDate.ToString("dd MMMM yyyy h:mm tt")));
                                message = message.Replace("@ViewBag.ShipDate", cultureInd.TextInfo.ToTitleCase(osDate.ToString("dd MMMM yyyy")));
                                message = message.Replace("@ViewBag.CustName", cultureInd.TextInfo.ToTitleCase(data.customer.CustName + "-" + data.CustId));
                                message = message.Replace("@ViewBag.Channel", cultureInd.TextInfo.ToTitleCase(data.customer.channel.ChannelName));
                                message = message.Replace("@ViewBag.SalesName", cultureInd.TextInfo.ToTitleCase(data.sales.SalesmanName));
                                double itemtotal = 0;
                                double itemvalue = 0;
                                string isibody = null;
                                int nourut = 1;
                                List<mPoLine> listbonus = new List<mPoLine>();
                                List<mPoLine> listitem = new List<mPoLine>();
                                foreach (mPoLine line in data.poLines)
                                {
                                    if (line.Qty * line.UnitPrice != line.DiscRp)
                                    {
                                        listitem.Add(line);
                                    }
                                    else
                                    {
                                        line.Selected = false;
                                        listbonus.Add(line);
                                    }
                                }
                                foreach (mPoLine dt in listitem)
                                {
                                    string bonuscode = "-", bonusname = "-", bonusunit = "";
                                    double bonusqty = 0;
                                    //add bonus
                                    foreach (mPoLine lb in listbonus)
                                    {
                                        if (dt.RecIdTab.Equals(lb.RefRecIdTab) && !lb.Selected)
                                        {
                                            bonuscode = lb.ProductCode;
                                            bonusname = lb.ProductName;
                                            bonusqty = lb.Qty;
                                            bonusunit = lb.UnitId;
                                            lb.Selected = true;
                                            break;
                                        }
                                    }
                                    string discpk, diskgmk;
                                    if (custlevel == 1)
                                    {
                                        //jika customer direct
                                        discpk = po.Disc1 + "%+" + po.Disc2 + "%";
                                        diskgmk = dt.Disc1 + "%+" + dt.Disc2 + "%+" + dt.Disc3 + "%";
                                    }
                                    else
                                    {
                                        discpk = po.Disc2 + "%+" + dt.Disc2 + "%+ COD:" + po.CashDisc + "%";
                                        diskgmk = po.Disc1 + "%+" + dt.Disc1 + "%";
                                    }
                                    isibody += "<tr>" +
                                              "<td>" + nourut + "</td>" +
                                              "<td>" + dt.PoId + "</td>" +
                                              "<td>" + data.distBranch.CustCode + " </td>" +
                                              "<td>" + data.customer.CustName + "</td>" +
                                              "<td>" + data.distBranch.AreaName + " </ td >" +
                                              "<td>" + dt.ProductCode + "</td>" +
                                              "<td>" + dt.ProductName + "</td>" +
                                              "<td>" + dt.Qty + " " + dt.UnitId + "</td>" +
                                              "<td>-</td>" +
                                              "<td>" + bonusqty + " " + bonusunit + "</td> " +
                                              "<td>" + discpk + "</td>" +
                                              "<td>" + diskgmk + "</td>" +
                                              "<td>" + bonuscode + "</td>" +
                                              "<td>" + bonusname + "</td>" +
                                              "<td>" + dt.Qty + " " + dt.UnitId + "</td>" +
                                              "<td>" + ocDate.ToString("dd MMMM yyyy") + " s/d " + oeDate.ToString("dd MMMM yyyy") + "</td>" +
                                          "</tr>";
                                    nourut++;

                                    itemtotal += dt.Qty;
                                    double harga = 0;
                                    if (dt.IncludePPN)
                                    {
                                        harga = (dt.UnitPrice / 1.1) * 1;
                                    }
                                    else
                                    {
                                        harga = dt.UnitPrice;
                                    }
                                    double diskontotal = 0;
                                    //if (custlevel == 1)
                                    // {
                                    diskontotal = (1 - (1 - dt.Disc1 / 100) * (1 - dt.Disc2 / 100) * (1 - dt.Disc3 / 100));
                                    // }else
                                    // {
                                    //     diskontotal = (1 - (1 - dt.Disc1 / 100) * (1 - dt.Disc2 / 100));
                                    // }
                                    itemvalue += dt.Qty * (harga - (harga * diskontotal) - dt.DiscRp);
                                }
                                double diskonheaderdist = (1 - (1 - data.Disc2 / 100) * (1 - data.CashDisc / 100));
                                double diskontotalheader = (1 - (1 - data.Disc1 / 100) * (1 - diskonheaderdist / 100));
                                double itemvalueheader = itemvalue - (itemvalue * diskontotalheader);
                                message = message.Replace("@ViewBag.QtyProduk", cultureInd.TextInfo.ToTitleCase(itemtotal.ToString()));
                                message = message.Replace("@ViewBag.Value", cultureInd.TextInfo.ToTitleCase(String.Format(cultureInd, "{0:C}", itemvalueheader)));
                                message = message.Replace("@ViewBag.StatusOrder", cultureInd.TextInfo.ToTitleCase(data.PoStatusName));
                                message = message.Replace("@ViewBag.PicCust", cultureInd.TextInfo.ToTitleCase(data.PicCust));
                                message = message.Replace("@ViewBag.DateSend", cultureInd.TextInfo.ToTitleCase(DateTime.Now.ToString("dd MMMM yyyy h:mm tt")));
                                message = message.Replace("@ViewBody.TableValue", isibody);
                                altView = AlternateView.CreateAlternateViewFromString(message, null, MediaTypeNames.Text.Html);
                                if (data.Signature != null || data.Signature != "")
                                {

                                    if (File.Exists(HostingEnvironment.MapPath("~/uploads/") + data.Signature))
                                    {
                                        LinkedResource yourPictureRes = new LinkedResource(HostingEnvironment.MapPath("~/uploads/") + data.Signature, MediaTypeNames.Image.Jpeg);
                                        yourPictureRes.ContentId = Guid.NewGuid().ToString();
                                        message = message.Replace("@ViewBag.Signature", yourPictureRes.ContentId);
                                        altView = AlternateView.CreateAlternateViewFromString(message, null, MediaTypeNames.Text.Html);
                                        altView.LinkedResources.Add(yourPictureRes);
                                    }

                                }

                            }
                            else
                            {
                                ExcelFile excel = new ExcelFile();
                                List<mWfTrans> sign = sp.getSignByPoId(data.PoCustNumberRef);
                                mPO popp = sp.getPoById(data.PoCustNumberRef);
                                if (popp != null)
                                    attachpdf = excel.ExportExcelPP(popp, sign);

                                if (attachpdf != null)
                                {
                                    attachment.Add(attachpdf);
                                    partialPP = "Merujuk PP NO:" + data.PoCustNumberRef;
                                }
                                attach = excel.ExportExcelNew(data, custlevel, partialPP);
                                if (attach != null)
                                {
                                    attachment.Add(attach);
                                }
                                message = message.Replace("ViewBag.SubjectMail", cultureInd.TextInfo.ToTitleCase(subject));
                                message = message.Replace("@ViewBag.PicDist", cultureInd.TextInfo.ToTitleCase(data.PicDist));
                                message = message.Replace("@ViewBag.PoId", cultureInd.TextInfo.ToTitleCase(data.PoId));
                                if (partialPP != null)
                                {
                                    message = message.Replace("@ViewBag.PoRefId", cultureInd.TextInfo.ToTitleCase(partialPP));
                                }
                                else
                                {
                                    message = message.Replace("@ViewBag.PoRefId", cultureInd.TextInfo.ToTitleCase(data.PoCustNumberRef));
                                }
                                message = message.Replace("@ViewBag.PoDate", cultureInd.TextInfo.ToTitleCase(opDate.ToString("dd MMMM yyyy h:mm tt")));
                                message = message.Replace("@ViewBag.SalesConfirmDate", cultureInd.TextInfo.ToTitleCase(ocoDate.ToString("dd MMMM yyyy h:mm tt")));
                                message = message.Replace("@ViewBag.ShipDate", cultureInd.TextInfo.ToTitleCase(osDate.ToString("dd MMMM yyyy")));
                                message = message.Replace("@ViewBag.CustName", cultureInd.TextInfo.ToTitleCase(data.customer.CustName + "-" + data.CustId));
                                message = message.Replace("@ViewBag.Channel", cultureInd.TextInfo.ToTitleCase(data.customer.channel.ChannelName));
                                message = message.Replace("@ViewBag.SalesName", cultureInd.TextInfo.ToTitleCase(data.sales.SalesmanName));
                                message = message.Replace("@ViewBag.By", cultureInd.TextInfo.ToTitleCase(data.PoByName));
                                message = message.Replace("@ViewBag.Via", cultureInd.TextInfo.ToTitleCase(data.PoViaName));

                                double itemtotal = 0;
                                double itemvalue = 0;
                                foreach (mPoLine dt in data.poLines)
                                {
                                    itemtotal += dt.Qty;
                                    double harga = 0;
                                    if (dt.IncludePPN)
                                    {
                                        harga = (dt.UnitPrice / 1.1) * 1;
                                    }
                                    else
                                    {
                                        harga = dt.UnitPrice;
                                    }
                                    double diskontotal = 0;//= (1 - (1 - dt.Disc1 / 100) * (1 - dt.Disc2 / 100));
                                    diskontotal = (1 - (1 - dt.Disc1 / 100) * (1 - dt.Disc2 / 100) * (1 - dt.Disc3 / 100));
                                    itemvalue += dt.Qty * (harga - (harga * diskontotal) - dt.DiscRp);
                                }
                                double diskonheaderdist = (1 - (1 - data.Disc2 / 100) * (1 - data.CashDisc / 100));
                                double diskontotalheader = (1 - (1 - data.Disc1 / 100) * (1 - diskonheaderdist / 100));
                                double itemvalueheader = itemvalue - (itemvalue * diskontotalheader);
                                message = message.Replace("@ViewBag.QtyProduk", cultureInd.TextInfo.ToTitleCase(itemtotal.ToString()));
                                message = message.Replace("@ViewBag.Value", cultureInd.TextInfo.ToTitleCase(String.Format(cultureInd, "{0:C}", itemvalueheader)));
                                message = message.Replace("@ViewBag.StatusOrder", cultureInd.TextInfo.ToTitleCase(data.PoStatusName));
                                message = message.Replace("@ViewBag.Note", cultureInd.TextInfo.ToTitleCase(data.Notes));
                                message = message.Replace("@ViewBag.AlamatKirim", cultureInd.TextInfo.ToTitleCase(data.ShipAddress));
                                message = message.Replace("@ViewBag.PicCust", cultureInd.TextInfo.ToTitleCase(data.PicCust));
                                message = message.Replace("@ViewBag.DateSend", cultureInd.TextInfo.ToTitleCase(DateTime.Now.ToString("dd MMMM yyyy h:mm tt")));

                                if (data.Signature != null && !data.Signature.Equals(""))
                                {

                                    if (File.Exists(HostingEnvironment.MapPath("~/uploads/") + data.Signature))
                                    {
                                        LinkedResource yourPictureRes = new LinkedResource(HostingEnvironment.MapPath("~/uploads/") + data.Signature, MediaTypeNames.Image.Jpeg);
                                        yourPictureRes.ContentId = Guid.NewGuid().ToString();
                                        message = message.Replace("@ViewBag.Signature", yourPictureRes.ContentId);
                                        altView = AlternateView.CreateAlternateViewFromString(message, null, MediaTypeNames.Text.Html);
                                        altView.LinkedResources.Add(yourPictureRes);
                                    }

                                }
                            }
                            List<MailAddress> emailTo = new List<MailAddress>();
                            List<MailAddress> emailcc = new List<MailAddress>();
                            if (data.isPP)
                            {
                                //kirim email ke atasan ABM untuk persetujuan
                                if (data.sales.salesAtasan != null)
                                {
                                    //spv lv 8
                                    if (data.sales.salesAtasan.Email != null && !data.sales.salesAtasan.Email.Equals(""))
                                    {
                                        foreach (var address in data.sales.salesAtasan.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                        {
                                            emailTo.Add(new MailAddress(address, address));
                                        }
                                    }
                                    //abm lv 7
                                    if (data.sales.salesAtasan.salesAtasan != null)
                                    {
                                        if (data.sales.salesAtasan.salesAtasan.Email != null && !data.sales.salesAtasan.salesAtasan.Email.Equals(""))
                                        {
                                            foreach (var address in data.sales.salesAtasan.salesAtasan.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                            {
                                                emailTo.Add(new MailAddress(address, address));
                                            }
                                        }
                                        //nbm lv 6
                                        if (data.sales.salesAtasan.salesAtasan.salesAtasan != null)
                                        {
                                            if (data.sales.salesAtasan.salesAtasan.salesAtasan.Email != null && !data.sales.salesAtasan.salesAtasan.salesAtasan.Email.Equals(""))
                                            {
                                                foreach (var address in data.sales.salesAtasan.salesAtasan.salesAtasan.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
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
                            }
                            else
                            {
                                //ceh apakah hanya sell out saja
                                if (!data.isSellOut)
                                {
                                    if (data.distBranch.Email != null && !data.distBranch.Email.Equals(""))
                                    {
                                        foreach (var address in data.distBranch.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                        {
                                            emailTo.Add(new MailAddress(address, address));
                                        }
                                    }
                                    else
                                    {
                                        emailTo.Add(new MailAddress("system.geisaforce@gandummas.co.id", "Bounch No Address"));
                                    }
                                    if (data.distBranch.EmailInternal != null && !data.distBranch.EmailInternal.Equals(""))
                                    {
                                        foreach (var address in data.distBranch.EmailInternal.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                        {
                                            emailTo.Add(new MailAddress(address, address));
                                        }
                                    }
                                    if (data.customer.channel.Email != null && !data.customer.channel.Email.Equals(""))
                                    {
                                        foreach (var address in data.customer.channel.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                        {
                                            emailcc.Add(new MailAddress(address, address));
                                        }

                                    }
                                    //add untuk sales atasan langsung
                                    if (data.sales.salesAtasan != null)
                                    {
                                        //atasan langsung
                                        if (data.sales.salesAtasan.Email != null && !data.sales.salesAtasan.Email.Equals(""))
                                        {
                                            foreach (var address in data.sales.salesAtasan.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                            {
                                                emailcc.Add(new MailAddress(address, address));
                                            }
                                        }
                                    }
                                }

                            }

                            if (data.sales.Email != null && !data.sales.Email.Equals(""))
                            {
                                foreach (var address in data.sales.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                {
                                    emailcc.Add(new MailAddress(address, address));
                                }
                            }

                            if (!data.isSellOut)
                                if (data.customer.Email != null && !data.customer.Email.Equals(""))
                                {
                                    foreach (var address in data.customer.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                    {
                                        emailcc.Add(new MailAddress(address, address));
                                    }

                                }
                            await MessageServices.SendEmailAsync("system.geisaforce@gandummas.co.id", "System GeisaForce", emailTo, emailcc, subject, message, altView, attachment);
                        }
                    }
                    else
                    {
                        items = false;
                    }
                }
                catch (Exception ex)
                {
                    //gagal kirim email
                    items = false;
                    spLog log = new spLog();
                    string json = JsonConvert.SerializeObject(po);
                    log.inserLog("salesConfirmPO", ex.ToString() + "\n" + json, po.SalesmanId.ToString());
                }
            }
            if (items)
            {
                //FileInfo file = new FileInfo(attach);
                //if (file.Exists)
                //{
                //    file.Delete();
                //}
                return Request.CreateResponse(HttpStatusCode.OK, po);
            }
            else
            {
                return Request.CreateResponse(HttpStatusCode.InternalServerError, po);
            }
        }


        public static async Task<string> EmailTemplate(string template)
        {
            var templateFilePath = HostingEnvironment.MapPath("~/Content/templates/") + template + ".cshtml";
            StreamReader objStreamReaderFile = new StreamReader(templateFilePath);
            var body = await objStreamReaderFile.ReadToEndAsync();
            objStreamReaderFile.Close();
            return body;
        }


        [Route("GetPPActive")]
        [AcceptVerbs("Get")]
        public HttpResponseMessage GetPPActive(String salesId)
        {
            List<mPO> result = new List<mPO>();
            result = sp.getPoPPBySalesId(salesId);
            IQueryable<mPO> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);

        }


        //resend PO
        [Route("resendPO")]
        [AcceptVerbs("GET")]
        public async Task<HttpResponseMessage> resendPO(string poId)
        {
            //List<mPO> result = new List<mPO>();
            bool items = false;
            string attach = null, attachpdf = null;
            List<String> attachment = new List<string>();
            string partialPP = null;
            mPO data = sp.getPoById(poId);
            if (data != null)
            {

                try
                {

                    //data = sp.getPoById("2017/12/08-201080443");// ("2017/08/08-84104712");// ("2017 /08/08-73080836");
                    if (data != null)
                    {
                        WfTrans approval = null;
                        if (data.PoStatusId == 7)
                        {
                            items = true;
                        }
                        else
                        {

                            DateTime osDate = Convert.ToDateTime(data.ShipDate);
                            DateTime opDate = Convert.ToDateTime(data.PoDate);
                            DateTime ocoDate = Convert.ToDateTime(data.ConfirmDate);
                            DateTime ocDate = Convert.ToDateTime(data.CreatedDate);
                            DateTime oeDate = Convert.ToDateTime(data.EndPeriodeDate);
                            //send email to client with format po
                            AlternateView altView = null;
                            string namatemplate = "PoReguler";
                            var message = await EmailTemplate(namatemplate);
                            var subject = "[INFO PO] " + data.PoId + " For: ";
                            int custlevel = 0;
                            if (data.isSellOut)
                                subject = "[INFO SELLOUT] " + data.PoId + " For: ";
                            if (data.customer != null)
                            {
                                subject += data.customer.AliasName;
                                custlevel = data.customer.CustLevelId;
                            }
                            if (data.isPP)
                            {
                                ExcelFile excel = new ExcelFile();
                                attach = excel.ExportExcelNew(data, custlevel, partialPP);
                                if (attach != null)
                                {
                                    attachment.Add(attach);
                                }

                                namatemplate = "PoPP";
                                if (custlevel == 1)
                                    namatemplate = "PoPPDirect";
                                //cek untuk workflow PP
                                approval = sp.getWfTrans(data.PoId);
                                if (approval != null)
                                {
                                    if (approval.Index == 1)
                                    {
                                        if (approval.StatusActionWf == 1)
                                        {
                                            subject = "[NEED APPROVAL HO] " + data.PoId + " For: "; //kirim ulang ke HO
                                        }
                                        else
                                        {
                                            subject = "[INFO PP] " + data.PoId + " For: ";//kirim ulang ke ATASAN
                                        }

                                    }
                                    else if (approval.Index == 2)
                                    {
                                        // if (data.PoStatusId == 3)
                                        // {

                                        // }
                                        if (approval.StatusActionWf == 1)
                                        {
                                            subject = "[APPROVAL PP FROM GMK] " + data.PoId + " For: "; //kirim ulang ke distributor
                                        }
                                        else
                                        {
                                            subject = "[NEED APPROVAL HO] " + data.PoId + " For: ";//kirim ulang ke HO
                                        }
                                    }


                                }
                                else
                                {
                                    subject = "[INFO PP] " + data.PoId + " For: ";
                                }

                                if (data.customer != null)
                                    subject += data.customer.AliasName;

                                message = await EmailTemplate(namatemplate);
                                message = message.Replace("@ViewBag.SubjectMail", cultureInd.TextInfo.ToTitleCase(subject));
                                message = message.Replace("@ViewBag.PicDist", cultureInd.TextInfo.ToTitleCase(data.PicDist));
                                message = message.Replace("@ViewBag.Area", cultureInd.TextInfo.ToTitleCase(data.distBranch.AreaCode + "  " + data.distBranch.AreaName));
                                message = message.Replace("@ViewBag.PoId", cultureInd.TextInfo.ToTitleCase(data.PoId));
                                message = message.Replace("@ViewBag.PoRefId", cultureInd.TextInfo.ToTitleCase(data.PoCustNumberRef));
                                message = message.Replace("@ViewBag.PoDate", cultureInd.TextInfo.ToTitleCase(opDate.ToString("dd MMMM yyyy h:mm tt")));
                                message = message.Replace("@ViewBag.SalesConfirmDate", cultureInd.TextInfo.ToTitleCase(ocoDate.ToString("dd MMMM yyyy h:mm tt")));
                                message = message.Replace("@ViewBag.ShipDate", cultureInd.TextInfo.ToTitleCase(osDate.ToString("dd MMMM yyyy")));
                                message = message.Replace("@ViewBag.CustName", cultureInd.TextInfo.ToTitleCase(data.customer.CustName + "-" + data.CustId));
                                message = message.Replace("@ViewBag.Channel", cultureInd.TextInfo.ToTitleCase(data.customer.channel.ChannelName));
                                message = message.Replace("@ViewBag.SalesName", cultureInd.TextInfo.ToTitleCase(data.sales.SalesmanName));
                                double itemtotal = 0;
                                double itemvalue = 0;
                                string isibody = null;
                                int nourut = 1;
                                List<mPoLine> listbonus = new List<mPoLine>();
                                List<mPoLine> listitem = new List<mPoLine>();
                                foreach (mPoLine line in data.poLines)
                                {
                                    if (line.Qty * line.UnitPrice != line.DiscRp)
                                    {
                                        listitem.Add(line);
                                    }
                                    else
                                    {
                                        line.Selected = false;
                                        listbonus.Add(line);
                                    }
                                }
                                foreach (mPoLine dt in listitem)
                                {
                                    string bonuscode = "-", bonusname = "-", bonusunit = "";
                                    double bonusqty = 0;

                                    //add bonus
                                    foreach (mPoLine lb in listbonus)
                                    {
                                        if (dt.RecIdTab.Equals(lb.RefRecIdTab) && !lb.Selected)
                                        {
                                            bonuscode = lb.ProductCode;
                                            bonusname = lb.ProductName;
                                            bonusqty = lb.Qty;
                                            bonusunit = lb.UnitId;
                                            lb.Selected = true;
                                            break;
                                        }
                                    }
                                    string discpk, diskgmk;
                                    if (custlevel == 1)
                                    {
                                        //jika customer direct
                                        discpk = data.Disc1 + "%+" + data.Disc2 + "%";
                                        diskgmk = dt.Disc1 + "%+" + dt.Disc2 + "%+" + dt.Disc3 + "%";
                                    }
                                    else
                                    {
                                        discpk = data.Disc2 + "%+" + dt.Disc2 + "%+ COD:" + data.CashDisc + "%";
                                        diskgmk = data.Disc1 + "%+" + dt.Disc1 + "%";
                                    }
                                    isibody += "<tr>" +
                                                  "<td>" + nourut + "</td>" +
                                                  "<td>" + dt.PoId + "</td>" +
                                                  "<td>" + data.distBranch.CustCode + " </td>" +
                                                  "<td>" + data.customer.CustName + "</td>" +
                                                  "<td>" + data.distBranch.AreaName + " </ td >" +
                                                  "<td>" + dt.ProductCode + "</td>" +
                                                  "<td>" + dt.ProductName + "</td>" +
                                                  "<td>" + dt.Qty + " " + dt.UnitId + "</td>" +
                                                  "<td>-</td>" +
                                                  "<td>" + bonusqty + " " + bonusunit + "</td> " +
                                                  "<td>" + discpk + "</td>" +
                                                  "<td>" + diskgmk + "</td>" +
                                                  "<td>" + bonuscode + "</td>" +
                                                  "<td>" + bonusname + "</td>" +
                                                  "<td>" + dt.Qty + " " + dt.UnitId + "</td>" +
                                                  "<td>" + ocDate.ToString("dd MMMM yyyy") + " s/d " + oeDate.ToString("dd MMMM yyyy") + "</td>" +
                                              "</tr>";
                                    nourut++;

                                    itemtotal += dt.Qty;
                                    double harga = 0;
                                    if (dt.IncludePPN)
                                    {
                                        harga = (dt.UnitPrice / 1.1) * 1;
                                    }
                                    else
                                    {
                                        harga = dt.UnitPrice;
                                    }
                                    double diskontotal = 0;// = (1 - (1 - dt.Disc1 / 100) * (1 - dt.Disc2 / 100));
                                    diskontotal = (1 - (1 - dt.Disc1 / 100) * (1 - dt.Disc2 / 100) * (1 - dt.Disc3 / 100));
                                    itemvalue += dt.Qty * (harga - (harga * diskontotal) - dt.DiscRp);
                                }
                                double diskonheaderdist = (1 - (1 - data.Disc2 / 100) * (1 - data.CashDisc / 100));
                                double diskontotalheader = (1 - (1 - data.Disc1 / 100) * (1 - diskonheaderdist));
                                double itemvalueheader = itemvalue - (itemvalue * diskontotalheader);
                                message = message.Replace("@ViewBag.QtyProduk", cultureInd.TextInfo.ToTitleCase(itemtotal.ToString()));
                                message = message.Replace("@ViewBag.Value", cultureInd.TextInfo.ToTitleCase(String.Format(cultureInd, "{0:C}", itemvalueheader)));
                                message = message.Replace("@ViewBag.StatusOrder", cultureInd.TextInfo.ToTitleCase(data.PoStatusName));
                                message = message.Replace("@ViewBag.PicCust", cultureInd.TextInfo.ToTitleCase(data.PicCust));
                                message = message.Replace("@ViewBag.DateSend", cultureInd.TextInfo.ToTitleCase(DateTime.Now.ToString("dd MMMM yyyy h:mm tt")));
                                message = message.Replace("@ViewBody.TableValue", isibody);
                                altView = AlternateView.CreateAlternateViewFromString(message, null, MediaTypeNames.Text.Html);
                                if (data.Signature != null || data.Signature != "")
                                {

                                    if (File.Exists(HostingEnvironment.MapPath("~/uploads/") + data.Signature))
                                    {
                                        LinkedResource yourPictureRes = new LinkedResource(HostingEnvironment.MapPath("~/uploads/") + data.Signature, MediaTypeNames.Image.Jpeg);
                                        yourPictureRes.ContentId = Guid.NewGuid().ToString();
                                        message = message.Replace("@ViewBag.Signature", yourPictureRes.ContentId);
                                        altView = AlternateView.CreateAlternateViewFromString(message, null, MediaTypeNames.Text.Html);
                                        altView.LinkedResources.Add(yourPictureRes);
                                    }

                                }

                            }
                            else
                            {
                                ExcelFile excel = new ExcelFile();
                                List<mWfTrans> sign = sp.getSignByPoId(data.PoCustNumberRef);
                                mPO popp = sp.getPoById(data.PoCustNumberRef);
                                if (popp != null)
                                    attachpdf = excel.ExportExcelPP(popp, sign);
                                if (attachpdf != null)
                                {
                                    attachment.Add(attachpdf);
                                    partialPP = "Merujuk PP NO:" + data.PoCustNumberRef;
                                }
                                attach = excel.ExportExcelNew(data, custlevel, partialPP);
                                if (attach != null)
                                {
                                    attachment.Add(attach);
                                }

                                message = message.Replace("ViewBag.SubjectMail", cultureInd.TextInfo.ToTitleCase(subject));
                                message = message.Replace("@ViewBag.PicDist", cultureInd.TextInfo.ToTitleCase(data.PicDist));
                                message = message.Replace("@ViewBag.PoId", cultureInd.TextInfo.ToTitleCase(data.PoId));
                                if (partialPP != null)
                                {
                                    message = message.Replace("@ViewBag.PoRefId", cultureInd.TextInfo.ToTitleCase(partialPP));
                                }
                                else
                                {
                                    message = message.Replace("@ViewBag.PoRefId", cultureInd.TextInfo.ToTitleCase(data.PoCustNumberRef));
                                }
                                message = message.Replace("@ViewBag.PoDate", cultureInd.TextInfo.ToTitleCase(opDate.ToString("dd MMMM yyyy h:mm tt")));
                                message = message.Replace("@ViewBag.SalesConfirmDate", cultureInd.TextInfo.ToTitleCase(ocoDate.ToString("dd MMMM yyyy h:mm tt")));
                                message = message.Replace("@ViewBag.ShipDate", cultureInd.TextInfo.ToTitleCase(osDate.ToString("dd MMMM yyyy")));
                                message = message.Replace("@ViewBag.CustName", cultureInd.TextInfo.ToTitleCase(data.customer.CustName + "-" + data.CustId));
                                message = message.Replace("@ViewBag.Channel", cultureInd.TextInfo.ToTitleCase(data.customer.channel.ChannelName));
                                message = message.Replace("@ViewBag.SalesName", cultureInd.TextInfo.ToTitleCase(data.sales.SalesmanName));
                                message = message.Replace("@ViewBag.By", cultureInd.TextInfo.ToTitleCase(data.PoByName));
                                message = message.Replace("@ViewBag.Via", cultureInd.TextInfo.ToTitleCase(data.PoViaName));

                                double itemtotal = 0;
                                double itemvalue = 0;
                                foreach (mPoLine dt in data.poLines)
                                {
                                    itemtotal += dt.Qty;
                                    double harga = 0;
                                    if (dt.IncludePPN)
                                    {
                                        harga = (dt.UnitPrice / 1.1) * 1;
                                    }
                                    else
                                    {
                                        harga = dt.UnitPrice;
                                    }
                                    double diskontotal = 0;// = (1 - (1 - dt.Disc1 / 100) * (1 - dt.Disc2 / 100));
                                    diskontotal = (1 - (1 - dt.Disc1 / 100) * (1 - dt.Disc2 / 100) * (1 - dt.Disc3 / 100));
                                    itemvalue += dt.Qty * (harga - (harga * diskontotal) - dt.DiscRp);
                                }
                                double diskonheaderdist = (1 - (1 - data.Disc2 / 100) * (1 - data.CashDisc / 100));
                                double diskontotalheader = (1 - (1 - data.Disc1 / 100) * (1 - diskonheaderdist));
                                double itemvalueheader = itemvalue - (itemvalue * diskontotalheader);
                                message = message.Replace("@ViewBag.QtyProduk", cultureInd.TextInfo.ToTitleCase(itemtotal.ToString()));
                                message = message.Replace("@ViewBag.Value", cultureInd.TextInfo.ToTitleCase(String.Format(cultureInd, "{0:C}", itemvalueheader)));
                                message = message.Replace("@ViewBag.StatusOrder", cultureInd.TextInfo.ToTitleCase(data.PoStatusName));
                                message = message.Replace("@ViewBag.Note", cultureInd.TextInfo.ToTitleCase(data.Notes));
                                message = message.Replace("@ViewBag.AlamatKirim", cultureInd.TextInfo.ToTitleCase(data.ShipAddress));
                                message = message.Replace("@ViewBag.PicCust", cultureInd.TextInfo.ToTitleCase(data.PicCust));
                                message = message.Replace("@ViewBag.DateSend", cultureInd.TextInfo.ToTitleCase(DateTime.Now.ToString("dd MMMM yyyy h:mm tt")));

                                if (data.Signature != null && !data.Signature.Equals(""))
                                {

                                    if (File.Exists(HostingEnvironment.MapPath("~/uploads/") + data.Signature))
                                    {
                                        LinkedResource yourPictureRes = new LinkedResource(HostingEnvironment.MapPath("~/uploads/") + data.Signature, MediaTypeNames.Image.Jpeg);
                                        yourPictureRes.ContentId = Guid.NewGuid().ToString();
                                        message = message.Replace("@ViewBag.Signature", yourPictureRes.ContentId);
                                        altView = AlternateView.CreateAlternateViewFromString(message, null, MediaTypeNames.Text.Html);
                                        altView.LinkedResources.Add(yourPictureRes);
                                    }

                                }
                            }



                            List<MailAddress> emailTo = new List<MailAddress>();
                            List<MailAddress> emailcc = new List<MailAddress>();
                            if (data.isPP)
                            {

                                // add untuk approval
                                if (approval != null)
                                {
                                    if (approval.Index == 1)
                                    {
                                        if (approval.StatusActionWf == 1)
                                        {
                                            //subject = "[NEED APPROVAL HO] " + data.PoId + " For: "; //kirim ulang ke HO
                                            if (approval.Email != null && !approval.Email.Equals(""))
                                            {
                                                foreach (var address in approval.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                                {
                                                    emailTo.Add(new MailAddress(address, address));
                                                }
                                            }
                                            else
                                            {
                                                //add nurjanah
                                                emailTo.Add(new MailAddress("nurjanah@gandummas.co.id", "Nurjanah"));
                                            }
                                            if (data.customer.channel.Email != null && !data.customer.channel.Email.Equals(""))
                                            {
                                                foreach (var address in data.customer.channel.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                                {
                                                    emailTo.Add(new MailAddress(address, address));
                                                }

                                            }

                                        }
                                        else
                                        {
                                            // subject = "[INFO PP] " + data.PoId + " For: ";//kirim ulang ke ATASAN
                                            //to atasan                                             //cc atasan
                                            if (data.sales.salesAtasan != null)
                                            {
                                                //spv lv 8
                                                if (data.sales.salesAtasan.Email != null && !data.sales.salesAtasan.Email.Equals(""))
                                                {
                                                    foreach (var address in data.sales.salesAtasan.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                                    {
                                                        emailcc.Add(new MailAddress(address, address));
                                                    }
                                                }
                                                //abm lv 7
                                                if (data.sales.salesAtasan.salesAtasan != null)
                                                {
                                                    if (data.sales.salesAtasan.salesAtasan.Email != null && !data.sales.salesAtasan.salesAtasan.Email.Equals(""))
                                                    {
                                                        foreach (var address in data.sales.salesAtasan.salesAtasan.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                                        {
                                                            emailcc.Add(new MailAddress(address, address));
                                                        }
                                                    }
                                                    //nbm lv 6
                                                    if (data.sales.salesAtasan.salesAtasan.salesAtasan != null)
                                                    {
                                                        if (data.sales.salesAtasan.salesAtasan.salesAtasan.Email != null && !data.sales.salesAtasan.salesAtasan.salesAtasan.Email.Equals(""))
                                                        {
                                                            foreach (var address in data.sales.salesAtasan.salesAtasan.salesAtasan.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                                            {
                                                                emailcc.Add(new MailAddress(address, address));
                                                            }
                                                        }

                                                    }
                                                }
                                            }

                                        }
                                        //cc admin area
                                        if (data.distBranch.EmailInternal != null && !data.distBranch.EmailInternal.Equals(""))
                                        {
                                            foreach (var address in data.distBranch.EmailInternal.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                            {
                                                emailcc.Add(new MailAddress(address, address));
                                            }
                                        }

                                    }
                                    else if (approval.Index == 2)
                                    {
                                        if (approval.StatusActionWf == 1)
                                        {
                                            //subject = "[APPROVAL PP FROM GMK] " + data.PoId + " For: "; //kirim ulang ke distributor
                                            if (data.distBranch.Email != null && !data.distBranch.Email.Equals(""))
                                            {
                                                foreach (var address in data.distBranch.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                                {
                                                    emailTo.Add(new MailAddress(address, address));
                                                }
                                            }
                                            else
                                            {
                                                emailTo.Add(new MailAddress("system.geisaforce@gandummas.co.id", "Bounch No Address"));
                                            }
                                            if (data.distBranch.EmailInternal != null && !data.distBranch.EmailInternal.Equals(""))
                                            {
                                                foreach (var address in data.distBranch.EmailInternal.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                                {
                                                    emailTo.Add(new MailAddress(address, address));
                                                }
                                            }
                                            if (data.customer.channel.Email != null && !data.customer.channel.Email.Equals(""))
                                            {
                                                foreach (var address in data.customer.channel.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                                {
                                                    emailcc.Add(new MailAddress(address, address));
                                                }

                                            }
                                            //add nurjanah
                                            if (approval.Email != null && !approval.Email.Equals(""))
                                            {
                                                foreach (var address in approval.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                                {
                                                    emailcc.Add(new MailAddress(address, address));
                                                }
                                            }
                                            else
                                            {
                                                emailTo.Add(new MailAddress("nurjanah@gandummas.co.id", "Nurjanah"));
                                            }
                                        }
                                        else
                                        {
                                            // subject = "[NEED APPROVAL HO] " + data.PoId + " For: ";//kirim ulang ke HO
                                            if (data.customer.channel.Email != null && !data.customer.channel.Email.Equals(""))
                                            {
                                                foreach (var address in data.customer.channel.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                                {
                                                    emailTo.Add(new MailAddress(address, address));
                                                }

                                            }
                                            //add nurjanah
                                            if (approval.Email != null && !approval.Email.Equals(""))
                                            {
                                                foreach (var address in approval.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                                {
                                                    emailTo.Add(new MailAddress(address, address));
                                                }
                                            }
                                            else
                                            {
                                                emailTo.Add(new MailAddress("nurjanah@gandummas.co.id", "Nurjanah"));
                                            }
                                            if (data.distBranch.EmailInternal != null && !data.distBranch.EmailInternal.Equals(""))
                                            {
                                                foreach (var address in data.distBranch.EmailInternal.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                                {
                                                    emailcc.Add(new MailAddress(address, address));
                                                }
                                            }
                                        }
                                        //cc atasan
                                        if (data.sales.salesAtasan != null)
                                        {
                                            //spv lv 8
                                            if (data.sales.salesAtasan.Email != null && !data.sales.salesAtasan.Email.Equals(""))
                                            {
                                                foreach (var address in data.sales.salesAtasan.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                                {
                                                    emailcc.Add(new MailAddress(address, address));
                                                }
                                            }
                                            //abm lv 7
                                            if (data.sales.salesAtasan.salesAtasan != null)
                                            {
                                                if (data.sales.salesAtasan.salesAtasan.Email != null && !data.sales.salesAtasan.salesAtasan.Email.Equals(""))
                                                {
                                                    foreach (var address in data.sales.salesAtasan.salesAtasan.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                                    {
                                                        emailcc.Add(new MailAddress(address, address));
                                                    }
                                                }
                                                //nbm lv 6
                                                if (data.sales.salesAtasan.salesAtasan.salesAtasan != null)
                                                {
                                                    if (data.sales.salesAtasan.salesAtasan.salesAtasan.Email != null && !data.sales.salesAtasan.salesAtasan.salesAtasan.Email.Equals(""))
                                                    {
                                                        foreach (var address in data.sales.salesAtasan.salesAtasan.salesAtasan.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                                        {
                                                            emailcc.Add(new MailAddress(address, address));
                                                        }
                                                    }
                                                }
                                            }

                                        }
                                    }


                                }
                                else
                                {
                                    // subject = "[INFO PP] " + data.PoId + " For: ";
                                    //kirim email ke atasan ABM untuk persetujuan
                                    if (data.sales.salesAtasan != null)
                                    {
                                        //spv lv 8
                                        if (data.sales.salesAtasan.Email != null && !data.sales.salesAtasan.Email.Equals(""))
                                        {
                                            foreach (var address in data.sales.salesAtasan.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                            {
                                                emailTo.Add(new MailAddress(address, address));
                                            }
                                        }
                                        //abm lv 7
                                        if (data.sales.salesAtasan.salesAtasan != null)
                                        {
                                            if (data.sales.salesAtasan.salesAtasan.Email != null && !data.sales.salesAtasan.salesAtasan.Email.Equals(""))
                                            {
                                                foreach (var address in data.sales.salesAtasan.salesAtasan.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                                {
                                                    emailTo.Add(new MailAddress(address, address));
                                                }
                                            }
                                            //nbm lv 6
                                            if (data.sales.salesAtasan.salesAtasan.salesAtasan != null)
                                            {
                                                if (data.sales.salesAtasan.salesAtasan.salesAtasan.Email != null && !data.sales.salesAtasan.salesAtasan.salesAtasan.Email.Equals(""))
                                                {
                                                    foreach (var address in data.sales.salesAtasan.salesAtasan.salesAtasan.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
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
                                }

                            }
                            else
                            {
                                if (!data.isSellOut)
                                {
                                    if (data.distBranch.Email != null && !data.distBranch.Email.Equals(""))
                                    {
                                        foreach (var address in data.distBranch.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                        {
                                            emailTo.Add(new MailAddress(address, address));
                                        }
                                    }
                                    else
                                    {
                                        emailTo.Add(new MailAddress("system.geisaforce@gandummas.co.id", "Bounch No Address"));
                                    }
                                    if (data.distBranch.EmailInternal != null && !data.distBranch.EmailInternal.Equals(""))
                                    {
                                        foreach (var address in data.distBranch.EmailInternal.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                        {
                                            emailTo.Add(new MailAddress(address, address));
                                        }
                                    }
                                    if (data.customer.channel.Email != null && !data.customer.channel.Email.Equals(""))
                                    {
                                        foreach (var address in data.customer.channel.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                        {
                                            emailcc.Add(new MailAddress(address, address));
                                        }

                                    }
                                    //add untuk sales atasan langsung
                                    if (data.sales.salesAtasan != null)
                                    {
                                        //atasan langsung
                                        if (data.sales.salesAtasan.Email != null && !data.sales.salesAtasan.Email.Equals(""))
                                        {
                                            foreach (var address in data.sales.salesAtasan.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                            {
                                                emailcc.Add(new MailAddress(address, address));
                                            }
                                        }
                                    }
                                }

                            }

                            if (data.sales.Email != null && !data.sales.Email.Equals(""))
                            {
                                foreach (var address in data.sales.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                {
                                    emailcc.Add(new MailAddress(address, address));
                                }
                            }

                            if (!data.isSellOut)
                                if (data.customer.Email != null && !data.customer.Email.Equals(""))
                                {
                                    foreach (var address in data.customer.Email.Split(new[] { ";" }, StringSplitOptions.RemoveEmptyEntries))
                                    {
                                        emailcc.Add(new MailAddress(address, address));
                                    }

                                }
                            items = true;
                            await MessageServices.SendEmailAsync("system.geisaforce@gandummas.co.id", "System GeisaForce", emailTo, emailcc, subject, message, altView, attachment);
                        }
                    }
                    else
                    {
                        items = false;
                    }
                }
                catch (Exception ex)
                {
                    //gagal kirim email
                    items = false;
                    spLog log = new spLog();
                    string json = JsonConvert.SerializeObject(data);
                    log.inserLog("resendPO", ex.ToString() + "\n" + json, data.SalesmanId.ToString());
                }
            }
            if (items)
            {
                PostResult hsl = new PostResult();
                hsl.errNot = items;
                hsl.errMsg = "Success Send Email";
                return Request.CreateResponse(HttpStatusCode.OK, hsl);
            }
            else
            {
                PostResult hsl = new PostResult();
                hsl.errNot = items;
                hsl.errMsg = "Failed Send Email";
                return Request.CreateResponse(HttpStatusCode.InternalServerError, hsl);
            }
        }
    }
}
