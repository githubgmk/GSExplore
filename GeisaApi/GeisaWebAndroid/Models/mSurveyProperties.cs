using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mSurveyProperties
    {
        public int SurveyId { get; set; }
        public String IntroMessage { get; set; }
        public String EndMessage { get; set; }
        public bool SkipIntro { get; set; }
    }
}