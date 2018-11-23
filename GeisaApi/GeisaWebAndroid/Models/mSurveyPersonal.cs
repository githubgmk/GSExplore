using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mSurveyPersonal
    {
        public bool SkipPersonal { get; set; }
        public List<mSurveyPersonalValue> Value { get; set; }
    }
}