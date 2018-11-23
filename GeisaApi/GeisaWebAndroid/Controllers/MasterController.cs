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

    [RoutePrefix("api/Master")]
    public class MasterController : ApiController
    {
        private DataContext db = new DataContext();
        private spMaster sp = new spMaster();

        [Route("GetArea")]
        public HttpResponseMessage GetArea(String salesId)
        {
            // mCustomer item;
            List<mArea> items = new List<mArea>();
            items = sp.SelectAreaBySalesId(salesId);
            // items.Add(item);
            IQueryable<mArea> iqueryable = items.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, items);

        }

        [Route("GetLevel")]
        public HttpResponseMessage GetLevel(String salesId)
        {
            // mCustomer item;
            List<mLevel> items = new List<mLevel>();
            items = sp.SelectLevelBySalesId(salesId);
            // items.Add(item);
            IQueryable<mLevel> iqueryable = items.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, items);

        }

        [Route("GetUnit")]
        public HttpResponseMessage GetUnit(String salesId)
        {
            // mCustomer item;
            List<mUnit> items = new List<mUnit>();
            items = sp.SelectUnitBySalesId(salesId);
            // items.Add(item);
            IQueryable<mUnit> iqueryable = items.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, items);

        }


        [Route("GetBiType")]
        public HttpResponseMessage GetBiType(String salesId)
        {
            // mCustomer item;
            List<mBiCsType> items = new List<mBiCsType>();
            items = sp.SelectBiCsTypeBySalesId(salesId);
            // items.Add(item);
            IQueryable<mBiCsType> iqueryable = items.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, items);

        }

        [Route("GetChannel")]
        public HttpResponseMessage GetChannel(String salesId)
        {
            // mCustomer item;
            List<mChannel> items = new List<mChannel>();
            items = sp.SelectChannelBySalesId(salesId);
            // items.Add(item);
            IQueryable<mChannel> iqueryable = items.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, items);

        }

        [Route("GetZone")]
        public HttpResponseMessage GetZone(String salesId)
        {
            // mCustomer item;
            List<mZone> items = new List<mZone>();
            items = sp.SelectZoneBySalesId(salesId);
            // items.Add(item);
            IQueryable<mZone> iqueryable = items.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, items);

        }
        [Route("GetCustStatus")]
        public HttpResponseMessage GetCustStatus(String salesId)
        {
            // mCustomer item;
            List<mCustStatus> items = new List<mCustStatus>();
            items = sp.SelectCustStatusBySalesId(salesId);
            // items.Add(item);
            IQueryable<mCustStatus> iqueryable = items.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, items);

        }
        [Route("GetDist")]
        public HttpResponseMessage GetDist(String salesId)
        {
            // mCustomer item;
            List<mDistributor> items = new List<mDistributor>();
            items = sp.SelectDistributorBySalesId(salesId);
            // items.Add(item);
            IQueryable<mDistributor> iqueryable = items.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, items);

        }

        [Route("GetDistBranch")]
        public HttpResponseMessage GetDistBranch(String salesId)
        {
            // mCustomer item;
            List<mDistBranch> items = new List<mDistBranch>();
            items = sp.SelectDistBranchBySalesId(salesId);
            // items.Add(item);
            IQueryable<mDistBranch> iqueryable = items.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, items);

        }

        [Route("GetProduct")]
        public HttpResponseMessage GetProduct(String salesId)
        {
            // mCustomer item;
            List<mProduct> items = new List<mProduct>();
            items = sp.SelectProductBySalesId(salesId);
            // items.Add(item);
            IQueryable<mProduct> iqueryable = items.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, items);

        }

        [Route("GetProductPriceDiskon")]
        public HttpResponseMessage GetProductPriceDiskon(String salesId)
        {
            // mCustomer item;
            List<mProductPriceDiskon> items = new List<mProductPriceDiskon>();
            items = sp.SelectProductPriceDiskonBySalesId(salesId);
            // items.Add(item);
            IQueryable<mProductPriceDiskon> iqueryable = items.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, items);

        }

        [Route("GetProductStockDist")]
        public HttpResponseMessage GetProductStockDist(String Bulan,String Tahun,String BranchId)
        {
            // mCustomer item;
            List<mStockBranch> items = new List<mStockBranch>();
            items = sp.SelectProductStockBranch(Bulan,Tahun,BranchId);
            // items.Add(item);
            IQueryable<mStockBranch> iqueryable = items.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, items);

        }
        [Route("GetProductStockProduct")]
        public HttpResponseMessage GetProductStockProduct(String Bulan, String Tahun, String BranchId,String ProdukId)
        {
            // mCustomer item;
            List<mStock> items = new List<mStock>();
            items = sp.SelectProductStockProduct(Bulan, Tahun, BranchId,ProdukId);
            // items.Add(item);
            IQueryable<mStock> iqueryable = items.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, items);

        }

        [Route("GetPromo")]
        public HttpResponseMessage GetPromo(String salesId) //String salesId
        {
            // mCustomer item;
            List<mPromo> items = new List<mPromo>();
            //mPromo items = null;
            items = sp.SelectPromoBySalesId(salesId);//salesId
            IQueryable<mPromo> iqueryable = items.AsQueryable();
            return Request.CreateResponse(HttpStatusCode.OK, items);

        }

    }
}
