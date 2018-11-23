using GeisaWebAndroid.Models;
using GeisaWebAndroid.ProsesDB;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Threading.Tasks;
using System.Web;
using System.Diagnostics;
using GeisaWebAndroid.Utils;
using System.IO;
using System.Text;
using System.Web.Hosting;

namespace GeisaWebAndroid.Controllers
{
    [RoutePrefix("api/TransTracking")]
    public class TransTrackingController : ApiController
    {
        private DataContext db = new DataContext();
        private spTracking sp = new spTracking();

        [Route("addTracking")]
        [AcceptVerbs("POST", "PUT")]
        public HttpResponseMessage addTracking(mTracking data)
        {
            // mCustomer item;
            bool items = false;
          
            PostResult hsl = new PostResult();
            try
            {


                items = sp.insertUpdateTrackingBySalesId(data);
                hsl.errNot = items;
                hsl.errMsg = "Success";
                hsl.msgA = data.TrackingId;
                hsl.msgB = data.SalesmanId;
                // hsl.errTag = data.customerId;               
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("addTracking", ex.ToString(),data.TrackingId);
                hsl.errNot = items;
                hsl.errMsg = ex.ToString();
                // hsl.errTag = data.customerId;
            }
            //result.Add(hsl);
            //IQueryable<PostResult> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, hsl);


        }


        [Route("PostUserImage")]
        [AllowAnonymous]
        public async Task<HttpResponseMessage> PostUserImage()
        {
            // Dictionary<string, object> dict = new Dictionary<string, object>();
            List<PostResult> result = new List<PostResult>();
            PostResult hsl = new PostResult();
            try
            {

                var httpRequest = HttpContext.Current.Request;
                mTrackingPicture picturedata=null;
                foreach (string file in httpRequest.Files)
                {
                    HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.Created);

                    var postedFile = httpRequest.Files[file];
                    //String value = HttpContext.Current.Request.Form["idcallplan"] ?? "";
                    var dt = HttpContext.Current.Request.Form.AllKeys;
                    String isi = "";
                    String pictureName = "", TrackingPictureId = "", PictureRef = "",
                        Picture = "", StatusBattery = "", Note = "", CreatedDate = "", CreatedBy = "";
                    if (dt != null)
                    {
                        pictureName = (HttpContext.Current.Request.Form["name"] ?? "").Replace(@"\", "");
                        TrackingPictureId = (HttpContext.Current.Request.Form["TrackingPictureId"] ?? "").Replace(@"\", "");
                        PictureRef = (HttpContext.Current.Request.Form["PictureRef"] ?? "").Replace(@"\", "");
                        Picture = (HttpContext.Current.Request.Form["Picture"] ?? "").Replace(@"\", "");
                        StatusBattery = (HttpContext.Current.Request.Form["StatusBattery"] ?? "").Replace(@"\", "");
                        Note = (HttpContext.Current.Request.Form["Note"] ?? "").Replace(@"\", "");
                        CreatedDate = (HttpContext.Current.Request.Form["CreatedDate"] ?? "").Replace(@"\", "");
                        CreatedBy = (HttpContext.Current.Request.Form["CreatedBy"] ?? "").Replace(@"\", "");
                        picturedata = new mTrackingPicture();
                        picturedata.TrackingPictureId = TrackingPictureId;
                        picturedata.PictureRef = PictureRef;
                        picturedata.PictureName = pictureName;
                        picturedata.StatusBattery = StatusBattery;
                        picturedata.Note = Note;
                        picturedata.CreatedDate = CreatedDate;
                        picturedata.CreatedBy = CreatedBy;

                    }
                    // String values = HttpContext.Current.Request.Path["name"] ?? "";
                    if (postedFile != null && postedFile.ContentLength > 0)
                    {

                        int MaxContentLength = 1024 * 1024 * 5; //Size = 5 MB  

                        IList<string> AllowedFileExtensions = new List<string> { ".jpg", ".gif", ".png" };
                        var ext = postedFile.FileName.Substring(postedFile.FileName.LastIndexOf('.'));
                        var extension = ext.ToLower();
                        if (!AllowedFileExtensions.Contains(extension))
                        {

                            var message = string.Format("Please Upload image of type .jpg,.gif,.png.");

                            //dict.Add("error", message);
                            // List<PostResult> result = new List<PostResult>();
                            hsl.errNot = false;
                            hsl.errMsg = message;
                            result.Add(hsl);
                            IQueryable<PostResult> iqa = result.AsQueryable();
                            return Request.CreateResponse(HttpStatusCode.BadRequest, result);
                        }
                        else if (postedFile.ContentLength > MaxContentLength)
                        {

                            var message = string.Format("Please Upload a file upto 1 mb.");

                            //dict.Add("error", message);
                            hsl.errNot = false;
                            hsl.errMsg = message;
                            result.Add(hsl);
                            IQueryable<PostResult> iqs = result.AsQueryable();
                            return Request.CreateResponse(HttpStatusCode.BadRequest, result);
                        }
                        else
                        {



                            var filePath = HttpContext.Current.Server.MapPath("~/uploads/" + postedFile.FileName + extension);

                            postedFile.SaveAs(filePath);

                        }
                    }

                    var message1 = string.Format("Image Updated Successfully.");
                    if(TrackingPictureId!=null && TrackingPictureId.Length > 1)
                    {
                        if (sp.insertUpdateTrackingPicture(picturedata))
                        {
                            hsl.errNot = true;
                            hsl.errMsg = message1;
                            hsl.msgA = pictureName;
                            hsl.msgB = TrackingPictureId;
                            hsl.msgC = Picture;
                            hsl.msgD = StatusBattery;
                            hsl.msgE = Note;
                            hsl.msgF = CreatedDate;
                            hsl.msgG = CreatedBy;
                            result.Add(hsl);
                            IQueryable<PostResult> iq = result.AsQueryable();
                            //return Request.CreateErrorResponse(HttpStatusCode.OK, result);
                            return Request.CreateResponse(HttpStatusCode.OK, result); ;
                        }else
                        {
                            hsl.errNot = false;
                            hsl.errMsg = "Data Tracking Picture Not Saved";
                            IQueryable<PostResult> iq = result.AsQueryable();
                            //return Request.CreateErrorResponse(HttpStatusCode.OK, result);
                            return Request.CreateResponse(HttpStatusCode.OK, result); 
                        }

                    }else
                    {
                        hsl.errNot = false;
                        hsl.errMsg = "NO Data tracking picture";
                        IQueryable<PostResult> iq = result.AsQueryable();
                        //return Request.CreateErrorResponse(HttpStatusCode.OK, result);
                        return Request.CreateResponse(HttpStatusCode.OK, result);
                    }
                    
                    
                }
                var res = string.Format("Please Upload a image.");
                //dict.Add("error", res);
                hsl.errNot = false;
                hsl.errMsg = res;
                result.Add(hsl);
                IQueryable<PostResult> iqueryable = result.AsQueryable();
                return Request.CreateResponse(HttpStatusCode.NotFound, result);
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("PostUserImages 1", ex.ToString(), "xx");
                var res = string.Format("some Message");
                //dict.Add("error", res+ex.Message);
                hsl.errNot = false;
                hsl.errMsg = res + ex.Message;
                result.Add(hsl);
                IQueryable<PostResult> iqueryable = result.AsQueryable();
                return Request.CreateResponse(HttpStatusCode.NotFound, result);
            }
        }

        //resopon 
        [Route("PostUserImages")]
        [AllowAnonymous]
        public async Task<HttpResponseMessage> PostUserImages()
        {
            // Dictionary<string, object> dict = new Dictionary<string, object>();
            List<PostResult> result = new List<PostResult>();
            PostResult hsl = new PostResult();
            try
            {

                var httpRequest = HttpContext.Current.Request;
                mTrackingPicture picturedata = null;
                foreach (string file in httpRequest.Files)
                {
                    HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.Created);

                    var postedFile = httpRequest.Files[file];
                    //String value = HttpContext.Current.Request.Form["idcallplan"] ?? "";
                    var dt = HttpContext.Current.Request.Form.AllKeys;
                    String isi = "";
                  
                    if (dt != null)
                    {
                        picturedata = new mTrackingPicture();
                        picturedata.PictureName = (HttpContext.Current.Request.Form["name"] ?? "").Replace(@"\", "");
                        picturedata.TrackingPictureId = (HttpContext.Current.Request.Form["TrackingPictureId"] ?? "").Replace(@"\", "");
                        picturedata.PictureRef = (HttpContext.Current.Request.Form["PictureRef"] ?? "").Replace(@"\", "");
                        //Picture = (HttpContext.Current.Request.Form["Picture"] ?? "").Replace(@"\", "");
                        picturedata.StatusBattery = (HttpContext.Current.Request.Form["StatusBattery"] ?? "").Replace(@"\", "");
                        picturedata.Note = (HttpContext.Current.Request.Form["Note"] ?? "").Replace(@"\", "");
                        picturedata.CreatedDate = (HttpContext.Current.Request.Form["CreatedDate"] ?? "").Replace(@"\", "");
                        picturedata.CreatedBy = (HttpContext.Current.Request.Form["CreatedBy"] ?? "").Replace(@"\", "");

                    }
                    // String values = HttpContext.Current.Request.Path["name"] ?? "";
                    if (postedFile != null && postedFile.ContentLength > 0)
                    {

                        int MaxContentLength = 1024 * 1024 * 5; //Size = 5 MB  

                        IList<string> AllowedFileExtensions = new List<string> { ".jpg", ".gif", ".png" };
                        var ext = postedFile.FileName.Substring(postedFile.FileName.LastIndexOf('.'));
                        var extension = ext.ToLower();
                        if (!AllowedFileExtensions.Contains(extension))
                        {

                            var message = string.Format("Please Upload image of type .jpg,.gif,.png.");

                            //dict.Add("error", message);
                            // List<PostResult> result = new List<PostResult>();
                            hsl.errNot = false;
                            hsl.errMsg = message;
                           // result.Add(hsl);
                           // IQueryable<PostResult> iqa = result.AsQueryable();
                            return Request.CreateResponse(HttpStatusCode.BadRequest, hsl);
                        }
                        else if (postedFile.ContentLength > MaxContentLength)
                        {

                            var message = string.Format("Please Upload a file upto 1 mb.");

                            //dict.Add("error", message);
                            hsl.errNot = false;
                            hsl.errMsg = message;
                           // result.Add(hsl);
                            //IQueryable<PostResult> iqs = result.AsQueryable();
                            return Request.CreateResponse(HttpStatusCode.BadRequest, hsl);
                        }
                        else
                        {



                            var filePath = HttpContext.Current.Server.MapPath("~/uploads/" + postedFile.FileName);
                            if (File.Exists(filePath)){
                                FileInfo fo = new FileInfo(Path.Combine(HostingEnvironment.MapPath("~/uploads/"), postedFile.FileName));
                                fo.Delete();
                                postedFile.SaveAs(filePath);
                            }
                            else
                            {
                                postedFile.SaveAs(filePath);
                            }
                            

                        }
                    }

                    var message1 = string.Format("Image Updated Successfully.");
                    if (picturedata != null & picturedata.TrackingPictureId.Length>1)
                    {
                        if (sp.insertUpdateTrackingPicture(picturedata))
                        {
                            hsl.errNot = true;
                            hsl.errMsg = message1;
                            hsl.msgA = picturedata.TrackingPictureId;
                            hsl.msgB = picturedata.PictureRef;
                            hsl.msgC = picturedata.PictureName;
                            hsl.msgD = picturedata.StatusBattery;
                            hsl.msgE = picturedata.Note;
                            hsl.msgF = picturedata.CreatedDate;
                            hsl.msgG = picturedata.CreatedBy;
                            //result.Add(hsl);
                            //IQueryable<PostResult> iq = result.AsQueryable();
                            //return Request.CreateErrorResponse(HttpStatusCode.OK, result);
                            return Request.CreateResponse(HttpStatusCode.OK, hsl); ;
                        }
                        else
                        {
                            hsl.errNot = false;
                            hsl.errMsg = "Data Tracking Picture Not Saved";
                            //IQueryable<PostResult> iq = result.AsQueryable();
                            //return Request.CreateErrorResponse(HttpStatusCode.OK, result);
                            return Request.CreateResponse(HttpStatusCode.OK, hsl);
                        }

                    }
                    else
                    {
                        hsl.errNot = false;
                        hsl.errMsg = "NO Data tracking picture";
                        //IQueryable<PostResult> iq = result.AsQueryable();
                        //return Request.CreateErrorResponse(HttpStatusCode.OK, result);
                        return Request.CreateResponse(HttpStatusCode.OK, hsl);
                    }


                }
                var res = string.Format("Please Upload a image.");
                //dict.Add("error", res);
                hsl.errNot = false;
                hsl.errMsg = res;
               // result.Add(hsl);
               // IQueryable<PostResult> iqueryable = result.AsQueryable();
                return Request.CreateResponse(HttpStatusCode.NotFound, hsl);
            }
            catch (Exception ex)
            {
                var res = string.Format("some Message");
                //dict.Add("error", res+ex.Message);
                hsl.errNot = false;
                hsl.errMsg = res + ex.Message;
                // result.Add(hsl);
                // IQueryable<PostResult> iqueryable = result.AsQueryable();
                spLog log = new spLog();
                log.inserLog("PostUserImages", ex.ToString(), "xx");
                return Request.CreateResponse(HttpStatusCode.NotFound, hsl);
            }
        }

    }
}
