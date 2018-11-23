using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mSurvey
    {
        public int SurveyId { get; set; }
        public String SurveyName { get; set; }
        public String SurveyDesc { get; set; }
        public String SurveyType { get; set; }
        public int SurveyTypeRef { get; set; }
        public String SurveyTypeRefDesc { get; set; }
        public bool UsePeriode { get; set; }
        public String PeriodeStart { get; set; }
        public String PeriodeEnd { get; set; }
        public bool Status { get; set; }

        public mSurveyProperties SurveyProperties { get; set; }
        public mSurveyPersonal PersonalInformation { get; set; }
        public List<mSurveyQuestionaire> Questions { get; set; }



    }
}