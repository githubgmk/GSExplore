using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.Entity;

namespace GeisaWebAndroid.Models
{
    public class DataContext
    {
        public DbSet<mSales> Sales { get; set; }
    }
}