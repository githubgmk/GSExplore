using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mTrackingPicture
    {
        public String TrackingPictureId { get; set; }
        public String PictureRef { get; set; }
       // public String Picture { get; set; }
        public String StatusBattery { get; set; }
        public String Note { get; set; }
        public String CreatedDate { get; set; }
        public String CreatedBy { get; set; }
        public byte[] LogStringBaseData { get; set; }
        public byte[] Picture { get; set; }
        public String PictureName { get; set; }
    }
}