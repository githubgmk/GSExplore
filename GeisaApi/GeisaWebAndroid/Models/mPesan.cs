using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mPesan
    {
        public long id { get; set; }
        public int idPengirim { get; set; }
        public String pengirim { get; set; }
        public int idPenerima { get; set; }
        public String penerima { get; set; }
        public String judul { get; set; }
        public String isiPesan { get; set; }
        public String fcmid { get; set; }
        public String typePesan { get; set; }
        public String dateSend { get; set; }
        public String dateRead { get; set; }
        public String statusPesan { get; set; }
        public bool statusSend { get; set; }
        public String refId { get; set; }
    }
}