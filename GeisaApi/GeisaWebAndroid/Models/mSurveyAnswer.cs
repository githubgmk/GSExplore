using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mSurveyAnswer
    {
        public String AnswerId { get; set; }
        public String SurveyId { get; set; }
        public List<PersonalValue> PersonalInfo { get; set; }
        public List<AnswerValue> SurveyAnswer { get; set; }

        public bool StatusSend { get; set; }

        public class PersonalValue
        {
            public String idAsk;
            public List<mSurveyPersonalValue> value;
        }
        public class AnswerValue
        {
            public String idAsk { get; set; }
            public String Ask { get; set; }
            public String TypeAsk { get; set; }
            public List<mSurveyQuestionaireChoices> Answer { get; set; }
        }
    }
}