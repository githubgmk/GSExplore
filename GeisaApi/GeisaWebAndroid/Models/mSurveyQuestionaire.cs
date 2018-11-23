using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mSurveyQuestionaire
    {
        public int QuestionId { get; set; }
        public String QuestionType { get; set; }
        public String QuestionTitle { get; set; }
        public String Description { get; set; }
        public bool Required { get; set; }
        public bool RandomChoices { get; set; }
        public int Min { get; set; }
        public int Max { get; set; }
        public int NumberOfLines { get; set; }
        public List<mSurveyQuestionaireChoices> Choices { get; set; }
    }
}