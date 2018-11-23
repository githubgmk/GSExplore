using GeisaWebAndroid.Models;
using GeisaWebAndroid.ProsesDB;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace GeisaWebAndroid.Controllers
{
    [RoutePrefix("api/Rofo")]
    public class RofoController : ApiController
    {
        private DataContext db = new DataContext();
        private spRofo sp = new spRofo();
        [Route("addNewRofo")]
        [AcceptVerbs("POST", "PUT")]
        public HttpResponseMessage addNewRofo(List<mRofo> data)
        {
            List<mRofo> result = new List<mRofo>();
            result = sp.addUpdateRofo(data);
            IQueryable<mRofo> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);

        }

        [Route("geAktualisasi")]
        [AcceptVerbs("GET")]
        public HttpResponseMessage geAktualisasi(string salesId, string year)
        {
            List<mRofoAktualisasi> result = new List<mRofoAktualisasi>();
            result = sp.getDataAktualisasi(salesId,year);
            IQueryable<mRofoAktualisasi> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);

        }

        [Route("getTarget")]
        [AcceptVerbs("GET")]
        public HttpResponseMessage getTarget(string salesId, string year)
        {
            List<mRofoTarget> result = new List<mRofoTarget>();
            result = sp.getRofoTarget(salesId,year);
            IQueryable<mRofoTarget> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);

        }

        [Route("getRofo")]
        [AcceptVerbs("GET")]
        public HttpResponseMessage getRofo(string salesId, string year,string month)
        {
            List<mRofo> result = new List<mRofo>();
            result = sp.getRofo(salesId,year,month);
            IQueryable<mRofo> iqueryable = result.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, result);

        }
    }
}
