using GeisaWebAndroid.Models;
using GeisaWebAndroid.ProsesDB;
using OfficeOpenXml;
using OfficeOpenXml.Drawing;
using OfficeOpenXml.FormulaParsing;
using OfficeOpenXml.Style;
using Spire.Xls;
using System;
using System.Collections.Generic;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Web;
using System.Web.Hosting;

namespace GeisaWebAndroid.Export
{
    public class ExcelFile : spLog
    {
        //reff PP existing
        public string ExportExcelPP(mPO po, List<mWfTrans> sign)
        {
            try
            {
                string namafile = null;
                MemoryStream output = new MemoryStream();
                if (po != null)
                {
                    if (po.PoStatusId >= 3)
                    {
                        //get po ttd from approval
                        string sWebRootFolder = HostingEnvironment.MapPath("~/Content/templates/");
                        string sWebRootFolderNew = HostingEnvironment.MapPath("~/uploads/doc/");
                        string sWebRootFolderImg = HostingEnvironment.MapPath("~/uploads/");
                        string sWebRootFolderImgsign = HostingEnvironment.MapPath("~/uploads/sign");
                        DirectoryInfo dir = new DirectoryInfo(sWebRootFolderNew);
                        if (!dir.Exists)
                        {
                            dir.Create();
                        }
                        string sFileName = @"template_pp.xlsx";
                        int count = po.customer.CustName.Split(' ').Length - 1;
                        string newString;
                        if (count > 1)
                        {
                            newString = po.customer.CustName.Remove(po.customer.CustName.IndexOf(" ", po.customer.CustName.IndexOf(" ") + 1));
                        }
                        else
                        {
                            newString = po.customer.CustName;
                        }
                        string regexSearch = new string(System.IO.Path.GetInvalidFileNameChars()) + new string(System.IO.Path.GetInvalidPathChars());
                        Regex r = new Regex(string.Format("[{0}]", Regex.Escape(regexSearch)));
                        newString = r.Replace(newString, "").Replace(".", "");
                        string sFileNameNew;

                        if (po.distBranch.TemplateEmailKhususAttach != null && !po.distBranch.TemplateEmailKhususAttach.Equals(""))
                        {
                            sFileName = po.distBranch.TemplateEmailKhususAttach;
                        }
                        else
                        {
                            sFileName = @"template_pp.xlsx";
                        }
                        DateTime osDate = Convert.ToDateTime(po.EndPeriodeDate);
                        sFileNameNew = @"PP-" + newString + "- valid " + osDate.ToString("yyyy-MMM-dd");
                        FileInfo newFilePdf = new FileInfo(System.IO.Path.Combine(sWebRootFolderNew, sFileNameNew + ".pdf"));
                        if (newFilePdf.Exists)
                        {
                            return newFilePdf.FullName;
                        }
                        FileInfo newFile = new FileInfo(System.IO.Path.Combine(sWebRootFolderNew, sFileNameNew + ".xlsx"));
                        FileInfo template = new FileInfo(System.IO.Path.Combine(sWebRootFolder, sFileName));

                        using (ExcelPackage xlPackage = new ExcelPackage(newFile, template))
                        {
                            xlPackage.Workbook.Worksheets.Delete("RowData");
                            xlPackage.Workbook.Worksheets.Delete("PO");
                            ExcelWorksheet worksheetlampiran = xlPackage.Workbook.Worksheets["Lampiran PP"];


                            //ExcelCell cell;

                            const int startRow = 2, startRowPo = 11, startRowLampiran = 18;
                            int rowTtl = 24;
                            int row = startRow;

                            int rowLampiran = startRowLampiran;
                            int nomorurut = 1, rowttd = 53;
                            double totalvalue = 0, totalvaluepp = 0, totalorder = 0, allbonus = 0, alldiskon = 0;
                            List<mPoLine> listbonus = new List<mPoLine>();
                            List<mPoLine> listitem = new List<mPoLine>();
                            foreach (mPoLine line in po.poLines)
                            {
                                if (line.Qty * line.UnitPrice != line.DiscRp)
                                {
                                    listitem.Add(line);
                                }
                                else
                                {
                                    line.Selected = false;
                                    listbonus.Add(line);
                                    allbonus += line.DiscRp;
                                }
                            }
                            //add item
                            if (listitem.Count > 0)
                            {
                                foreach (mPoLine line in listitem)
                                {

                                    double harga = 0;
                                    if (line.IncludePPN)
                                    {
                                        harga = (line.UnitPrice / 1.1) * 1;
                                    }
                                    else
                                    {
                                        harga = line.UnitPrice;
                                    }
                                    double diskon = (1 - (1 - line.Disc1 / 100) * (1 - line.Disc2 / 100));
                                    double diskonpp = line.Disc1 / 100;
                                    alldiskon += ((line.Qty * harga) * diskonpp) + line.DiscRp;
                                    totalorder += line.Qty * harga;
                                    double itemvaluepp = line.Qty * (harga - (harga * diskonpp) - line.DiscRp);
                                    double itemvalue = line.Qty * (harga - (harga * diskon) - line.DiscRp);
                                    totalvaluepp += itemvaluepp;
                                    totalvalue += itemvalue;

                                    row++;


                                    //add lampiran PP
                                    if (po.isPP)
                                    {
                                        worksheetlampiran.Cells[rowLampiran, 3].Value = nomorurut;
                                        worksheetlampiran.Cells[rowLampiran, 4].Value = line.ProductName;
                                        worksheetlampiran.Cells[rowLampiran, 6].Value = line.Qty;
                                        worksheetlampiran.Cells[rowLampiran, 7].Value = line.UnitId;
                                        worksheetlampiran.Cells[rowLampiran, 8].Value = line.UnitPrice;
                                        worksheetlampiran.Cells[rowLampiran, 9].Value = po.Disc2 / 100;
                                        worksheetlampiran.Cells[rowLampiran, 10].Value = po.CashDisc / 100;
                                        worksheetlampiran.Cells[rowLampiran, 11].Value = line.Disc2 / 100;
                                        if (line.poLineBonus != null)
                                        {
                                            worksheetlampiran.Cells[rowLampiran, 12].Value = line.poLineBonus.Qty;//bonus unit
                                        }
                                        else
                                        {
                                            worksheetlampiran.Cells[rowLampiran, 12].Value = 0;
                                        }
                                        worksheetlampiran.Cells[rowLampiran, 13].Value = po.Disc1 / 100;
                                        worksheetlampiran.Cells[rowLampiran, 14].Value = line.Disc1 / 100;


                                        if (line.Qty * line.UnitPrice == line.DiscRp)
                                        {
                                            worksheetlampiran.Cells[rowLampiran, 6].Value = 0;
                                            worksheetlampiran.Cells[rowLampiran, 12].Value = line.Qty;
                                            worksheetlampiran.Cells[rowLampiran, 15].Value = 0;
                                            worksheetlampiran.Cells[rowLampiran, 16].Value = line.DiscRp;
                                            worksheetlampiran.Cells[rowLampiran, 17].Value = 0;
                                        }
                                        else
                                        {
                                            worksheetlampiran.Cells[rowLampiran, 12].Value = 0;
                                            worksheetlampiran.Cells[rowLampiran, 15].Value = worksheetlampiran.Calculate(worksheetlampiran.Cells[rowLampiran, 6] + "*" + worksheetlampiran.Cells[rowLampiran, 8]);
                                            worksheetlampiran.Cells[rowLampiran, 16].Value = worksheetlampiran.Calculate(worksheetlampiran.Cells[rowLampiran, 8] + "*" + worksheetlampiran.Cells[rowLampiran, 12]);
                                            worksheetlampiran.Cells[rowLampiran, 17].Value = worksheetlampiran.Calculate("(" + worksheetlampiran.Cells[rowLampiran, 15] + "*(1-(1-" + worksheetlampiran.Cells[rowLampiran, 13] + ")*(1-" + worksheetlampiran.Cells[rowLampiran, 14] + ")))"); // = (O18 * (1 - (1 - M18) * (1 - N18)))"
                                        }
                                        //add bonus
                                        foreach (mPoLine lb in listbonus)
                                        {
                                            if (line.ProductId.Equals(lb.ProductId) && line.UnitId.Equals(lb.UnitId) && line.ProductCode.Equals(lb.ProductCode) && !lb.Selected)
                                            {
                                                worksheetlampiran.Cells[rowLampiran, 12].Value = lb.Qty;
                                                worksheetlampiran.Cells[rowLampiran, 16].Value = worksheetlampiran.Calculate(worksheetlampiran.Cells[rowLampiran, 8] + "*" + worksheetlampiran.Cells[rowLampiran, 12]);
                                                lb.Selected = true;
                                                break;
                                            }
                                        }

                                        nomorurut++;
                                        rowLampiran++;
                                        if (rowLampiran > 45)
                                            rowttd++;
                                    }

                                }
                            }

                            //add bonus
                            if (listbonus.Count > 0)
                            {

                                foreach (mPoLine line in listbonus)
                                {
                                    //add bonus di lampiran PP
                                    if (po.isPP)
                                    {
                                        if (!line.Selected)
                                        {
                                            worksheetlampiran.Cells[rowLampiran, 3].Value = nomorurut;
                                            worksheetlampiran.Cells[rowLampiran, 4].Value = line.ProductName;
                                            worksheetlampiran.Cells[rowLampiran, 6].Value = line.Qty;
                                            worksheetlampiran.Cells[rowLampiran, 7].Value = line.UnitId;
                                            worksheetlampiran.Cells[rowLampiran, 8].Value = line.UnitPrice;
                                            worksheetlampiran.Cells[rowLampiran, 9].Value = po.Disc2 / 100;
                                            worksheetlampiran.Cells[rowLampiran, 9].Value = po.CashDisc / 100;
                                            worksheetlampiran.Cells[rowLampiran, 11].Value = line.Disc2 / 100;
                                            if (line.poLineBonus != null)
                                            {
                                                worksheetlampiran.Cells[rowLampiran, 12].Value = line.poLineBonus.Qty;//bonus unit
                                            }
                                            else
                                            {
                                                worksheetlampiran.Cells[rowLampiran, 12].Value = 0;
                                            }
                                            worksheetlampiran.Cells[rowLampiran, 13].Value = po.Disc1 / 100;
                                            worksheetlampiran.Cells[rowLampiran, 14].Value = line.Disc1 / 100;


                                            if (line.Qty * line.UnitPrice == line.DiscRp)
                                            {
                                                worksheetlampiran.Cells[rowLampiran, 6].Value = 0;
                                                worksheetlampiran.Cells[rowLampiran, 12].Value = line.Qty;
                                                worksheetlampiran.Cells[rowLampiran, 15].Value = 0;
                                                worksheetlampiran.Cells[rowLampiran, 16].Value = line.DiscRp;
                                                worksheetlampiran.Cells[rowLampiran, 17].Value = 0;
                                            }

                                            nomorurut++;
                                            rowLampiran++;

                                            if (rowLampiran > 45)
                                                rowttd++;
                                        }
                                    }
                                }
                            }

                            //untuk PO
                            rowTtl += 1;
                            double diskonheaderdist = (1 - (1 - po.Disc2 / 100) * (1 - po.CashDisc / 100));

                            double diskontotal = (1 - (1 - po.Disc1 / 100) * (1 - diskonheaderdist));
                            double diskonppttl = po.Disc1 / 100;
                            alldiskon += totalvaluepp * diskonppttl;
                            double itemvaluetotal = totalvalue - (totalvalue * diskontotal);
                            double ppn = itemvaluetotal * 0.1;
                            double ttl = itemvaluetotal + ppn;

                            string notes;
                            if (po.CashDisc > 0)
                            {
                                notes = "Total Disc 2 adalah " + diskonheaderdist + " % dengan Rincian" + Environment.NewLine +
                                   "Disc Distributor " + po.Disc2 + " % dan COD Diskon " + po.CashDisc + " %" + Environment.NewLine + po.Notes;
                            }
                            else
                            {
                                notes = po.Notes;
                            }
                         
                            //sheet PP
                            if (po.isPP)
                            {
                                ExcelWorksheet worksheetPP = xlPackage.Workbook.Worksheets["PP"];
                                //nanti untuk pp ditambahkan
                                DateTime opDate = Convert.ToDateTime(po.PoDate);
                                DateTime spDate = Convert.ToDateTime(po.ShipDate);
                                DateTime onDate = Convert.ToDateTime(po.EndPeriodeDate);
                                worksheetPP.Cells[8, 4].Value = opDate.ToString("dd MMMM yyyy");
                                worksheetPP.Cells[9, 4].Value = po.distBranch.AreaName;
                                worksheetPP.Cells[10, 4].Value = po.PoId;
                                worksheetPP.Cells[9, 9].Value = po.PoId;
                                worksheetPP.Cells[13, 5].Value = po.PoId;
                                worksheetPP.Cells[14, 5].Value = po.customer.channel.ChannelName;
                                worksheetPP.Cells[15, 5].Value = opDate.ToString("dd MMMM yyyy") + "-" + onDate.ToString("dd MMMM yyyy");
                                worksheetPP.Cells[23, 5].Value = po.Mechanisme;
                                worksheetPP.Cells[37, 5].Value = po.distBranch.AreaName;
                                worksheetPP.Cells[38, 5].Value = po.customer.CustName;
                                //worksheetPP.Cells[41, 5].Value = itemvaluetotal;
                                worksheetPP.Cells[43, 6].Value = "All Product";
                                worksheetPP.Cells[43, 14].Value = totalorder;
                                worksheetPP.Cells[47, 6].Value = "All Bonus";
                                worksheetPP.Cells[47, 14].Value = allbonus;//bonus
                                worksheetPP.Cells[48, 6].Value = "All Diskon";
                                worksheetPP.Cells[48, 14].Value = alldiskon;//diskon

                                worksheetlampiran.Cells[3, 5].Value = opDate.ToString("dd MMMM yyyy");
                                worksheetlampiran.Cells[4, 5].Value = spDate.ToString("dd MMMM yyyy");
                                worksheetlampiran.Cells[9, 11].Value = po.customer.CustName;
                                worksheetlampiran.Cells[10, 11].Value = po.customer.Address;
                                worksheetlampiran.Cells[12, 11].Value = po.ShipAddress;
                                worksheetlampiran.Cells[14, 11].Value = po.customer.Telp;


                                //ttd lampiran
                                FileInfo fo = new FileInfo(System.IO.Path.Combine(sWebRootFolderImg, po.Signature));
                                if (fo.Exists)
                                {
                                    //salesman
                                    System.Drawing.Image logo = System.Drawing.Image.FromFile(fo.FullName);
                                    var picture = worksheetlampiran.Drawings.AddPicture(po.Signature, logo);
                                    picture.SetSize(140, 105);
                                    picture.SetPosition(rowttd, 0, 3, 0);
                                }
                                worksheetlampiran.Cells[rowttd + 6, 4].Value = po.PicCust;
                                //fileinfo NBM
                                FileInfo foNbm = null, fodbm = null;
                                string fonbmnama = "", fodbmnama = "",fodbmposisi="DBM",nbamactiondate="Tgl: ";
                                //kolom abm or dbm
                                if (po.sales.salesAtasan != null)
                                {
                                    bool salesabm = false;
                                    if (po.sales.salesAtasan.SalesmanLevelId == 7 || po.sales.salesAtasan.SalesmanLevelId == 6) //&& po.sales.salesAtasan.AllowApproval
                                    {
                                        fodbm = new FileInfo(System.IO.Path.Combine(sWebRootFolderImgsign, po.sales.salesAtasan.SignatureName));
                                        if (fodbm.Exists)
                                        {
                                            System.Drawing.Image logo = System.Drawing.Image.FromFile(fodbm.FullName);
                                            var picture = worksheetlampiran.Drawings.AddPicture(po.Signature + "atasan", logo);
                                            picture.SetSize(140, 105);
                                            picture.SetPosition(rowttd, 0, 13, 0);
                                        }
                                        worksheetlampiran.Cells[rowttd + 6, 14].Value = po.sales.salesAtasan.SalesmanName;
                                        fodbmnama = po.sales.salesAtasan.SalesmanName;
                                        if (po.sales.salesAtasan.SalesmanLevelId == 7)
                                            fodbmposisi = "ABM";
                                        salesabm = true;
                                    }
                                    else
                                    {
                                        if (po.sales.salesAtasan.salesAtasan != null)
                                        {
                                            if (po.sales.salesAtasan.salesAtasan.SalesmanLevelId == 7 || po.sales.salesAtasan.SalesmanLevelId == 6)
                                            {
                                                fodbm = new FileInfo(System.IO.Path.Combine(sWebRootFolderImgsign, po.sales.salesAtasan.salesAtasan.SignatureName));
                                                if (fodbm.Exists)
                                                {
                                                    System.Drawing.Image logo = System.Drawing.Image.FromFile(fodbm.FullName);
                                                    var picture = worksheetlampiran.Drawings.AddPicture(po.Signature + "atasan", logo);
                                                    picture.SetSize(140, 105);
                                                    picture.SetPosition(rowttd, 0, 13, 0);
                                                }
                                                worksheetlampiran.Cells[rowttd + 5, 14].Value = po.sales.salesAtasan.salesAtasan.SalesmanName;
                                                fodbmnama = po.sales.salesAtasan.SalesmanName;
                                                if (po.sales.salesAtasan.SalesmanLevelId == 7)
                                                    fodbmposisi = "ABM";
                                            }
                                            else if (po.sales.salesAtasan.salesAtasan.SalesmanLevelId == 5)
                                            {
                                                foNbm = new FileInfo(System.IO.Path.Combine(sWebRootFolderImgsign, po.sales.salesAtasan.salesAtasan.SignatureName));
                                                fonbmnama = po.sales.salesAtasan.salesAtasan.SalesmanName;
                                            }
                                        }
                                    }
                                    if (salesabm)
                                    {
                                        if (po.sales.salesAtasan.salesAtasan != null)
                                        {
                                            if (po.sales.salesAtasan.salesAtasan.SalesmanLevelId == 5)
                                            {
                                                foNbm = new FileInfo(System.IO.Path.Combine(sWebRootFolderImgsign, po.sales.salesAtasan.salesAtasan.SignatureName));
                                                fonbmnama = po.sales.salesAtasan.salesAtasan.SalesmanName;
                                            }
                                        }
                                    }
                                }

                                //utk spv approval silahkan ditambahak dimari untuk tanda tangan PP
                                int rowttdpp = 64;
                                bool dbm = false, nbm = false, cm = false, deputy = false;
                                foreach (mWfTrans wf in sign)
                                {
                                    if (wf.SalesmanLevelId == 7 || wf.SalesmanLevelId == 6)
                                    {
                                        //DBM or ABM
                                        FileInfo fosabm = new FileInfo(System.IO.Path.Combine(sWebRootFolderImgsign, wf.SignatureName));
                                        if (fosabm.Exists)
                                        {
                                            System.Drawing.Image logo = System.Drawing.Image.FromFile(fosabm.FullName);
                                            var picture = worksheetPP.Drawings.AddPicture(wf.SignatureName + "abm", logo);
                                            picture.SetSize(140, 105);
                                            picture.SetPosition(rowttdpp, 0, 2, 0);
                                        }
                                        worksheetPP.Cells[rowttdpp + 5, 2].Value = wf.SalesmanName;
                                        worksheetPP.Cells[rowttdpp -1, 2].Value = "Tgl: " + wf.ActionDate;
                                        dbm = true;
                                        if (po.sales.salesAtasan.SalesmanLevelId == 7)
                                            worksheetPP.Cells[rowttdpp + 6, 2].Value = "ABM";
                                    }
                                    else if (wf.SalesmanLevelId == 7 || wf.SalesmanLevelId == 6)
                                    {
                                        //DBM or ABM
                                        FileInfo fosdbm = new FileInfo(System.IO.Path.Combine(sWebRootFolderImgsign, wf.SignatureName));
                                        if (fosdbm.Exists)
                                        {
                                            System.Drawing.Image logo = System.Drawing.Image.FromFile(fosdbm.FullName);
                                            var picture = worksheetPP.Drawings.AddPicture(wf.SignatureName + "spv", logo);
                                            picture.SetSize(140, 105);
                                            picture.SetPosition(rowttd, 0, 2, 0);
                                        }
                                        worksheetPP.Cells[rowttdpp + 5, 2].Value = wf.SalesmanName;
                                        worksheetPP.Cells[rowttdpp - 1, 2].Value = "Tgl: " + wf.ActionDate;
                                        nbamactiondate = "Tgl: " + wf.ActionDate;
                                        dbm = true;
                                        if (po.sales.salesAtasan.SalesmanLevelId == 7)
                                            worksheetPP.Cells[rowttdpp + 6, 2].Value =  "ABM";
                                    }
                                    else if (wf.SalesmanLevelId == 5)
                                    {
                                        //NBM
                                        FileInfo fosnbm = new FileInfo(System.IO.Path.Combine(sWebRootFolderImgsign, wf.SignatureName));
                                        if (fosnbm.Exists)
                                        {
                                            System.Drawing.Image logo = System.Drawing.Image.FromFile(fosnbm.FullName);
                                            var picture = worksheetPP.Drawings.AddPicture(wf.SignatureName + "nb", logo);
                                            picture.SetSize(140, 105);
                                            picture.SetPosition(rowttdpp, 0, 4, 0);
                                        }
                                        worksheetPP.Cells[rowttdpp + 5, 4].Value = wf.SalesmanName;
                                        worksheetPP.Cells[rowttdpp - 1, 4].Value = "Tgl: " + wf.ActionDate;
                                        nbamactiondate = "Tgl: " + wf.ActionDate;
                                        nbm = true;
                                    }
                                    else if (wf.SalesmanLevelId < 5)
                                    {
                                        //channel manager add signature
                                        FileInfo foscm = new FileInfo(System.IO.Path.Combine(sWebRootFolderImgsign, wf.SignatureName));
                                        if (foscm.Exists)
                                        {
                                            System.Drawing.Image logo = System.Drawing.Image.FromFile(foscm.FullName);
                                            var picture = worksheetPP.Drawings.AddPicture(wf.SignatureName + "cm", logo);
                                            picture.SetSize(140, 105);
                                            picture.SetPosition(rowttdpp, 0, 5, 25);
                                        }
                                        worksheetPP.Cells[rowttdpp + 5, 6].Value = wf.SalesmanName;
                                        worksheetPP.Cells[rowttdpp - 1, 6].Value ="Tgl: "+ wf.ActionDate;
                                        cm = true;
                                    }

                                }
                                if (!nbm)
                                {

                                    if (foNbm != null && foNbm.Exists)
                                    {
                                        System.Drawing.Image logo = System.Drawing.Image.FromFile(foNbm.FullName);
                                        var picture = worksheetPP.Drawings.AddPicture("nbm", logo);
                                        picture.SetSize(140, 105);
                                        picture.SetPosition(rowttdpp, 0, 4,-15);
                                        worksheetPP.Cells[rowttdpp + 5, 4].Value = fonbmnama;
                                        worksheetPP.Cells[rowttdpp - 1, 4].Value = nbamactiondate;
                                        
                                    }
                                    
                                }
                                if (!dbm)
                                {
                                    //dbm or abm
                                    if (foNbm != null && foNbm.Exists)
                                    {
                                        System.Drawing.Image logo = System.Drawing.Image.FromFile(foNbm.FullName);
                                        var picture = worksheetPP.Drawings.AddPicture("dbm", logo);
                                        picture.SetSize(140, 105);
                                        picture.SetPosition(rowttd, 0, 2, 0);
                                        worksheetPP.Cells[rowttdpp + 5, 2].Value = fodbmnama;
                                        worksheetPP.Cells[rowttdpp + 6, 2].Value = fodbmposisi;
                                    }
                                   
                                }
                                if (!cm)
                                {
                                    if (po.customer.channel != null)
                                    {
                                        FileInfo foscms = new FileInfo(System.IO.Path.Combine(sWebRootFolderImgsign, po.customer.channel.Signature));
                                        if (foscms.Exists)
                                        {
                                            System.Drawing.Image logo = System.Drawing.Image.FromFile(foscms.FullName);
                                            var picture = worksheetPP.Drawings.AddPicture(po.customer.channel.Signature + "cnanel", logo);
                                            picture.SetSize(140, 105);
                                            picture.SetPosition(rowttdpp, 0, 5, 25);
                                        }
                                        worksheetPP.Cells[rowttdpp + 5, 6].Value = po.customer.channel.Pic;
                                    }
                                   
                                }
                                // FileInfo fospv = new FileInfo(Path.Combine(sWebRootFolderImgsign, po.sales.salesAtasan.SignatureName));
                                //deputy gm
                                //        if (fospv.Exists)
                                //        {
                                //            Image logo = Image.FromFile(fospv.FullName);
                                //            var picture = worksheetlampiran.Drawings.AddPicture(po.Signature + "spv", logo);
                                //            picture.SetSize(140, 105);
                                //            picture.SetPosition(rowttd, 0, 13, 0);
                                //        }
                                //        worksheetlampiran.Cells[rowttd + 6, 14].Value = po.sales.salesAtasan.SalesmanName;
                                
                            }
                            xlPackage.SaveAs(output);
                            //xlPackage.Save();
                            namafile=ConvertXLSXtoPDF(output,newFile.Name);
                        }
                        return namafile;
                    }
                }
                return null;

            }
            catch (Exception ex)
            {
                string e = ex.ToString();
                inserLog("ExportExcelNew", e, po.SalesmanId.ToString());
                return null;
            }
        }

        public String ConvertXLSXtoPDF(MemoryStream stream,string fileName)
        {
           
            // Spire.XLS to open XLSX workbook stream created by EPPlus
            Spire.Xls.Workbook workbook = new Spire.Xls.Workbook();
            workbook.LoadFromStream(stream, Spire.Xls.ExcelVersion.Version2013);
            //workbook.LoadFromFile(fileName);
            workbook.CalculateAllValue();
            // Save and preview PDF  
            string sWebRootFolderNew = HostingEnvironment.MapPath("~/uploads/doc/");
            workbook.SaveToPdf(System.IO.Path.Combine(sWebRootFolderNew, fileName + ".pdf"), new Spire.Xls.Converter.PdfConverterSettings
            {
                FitSheetToOnePage = Spire.Xls.Converter.FitToPageType.NoScale
            });
            //workbook.SaveToFile(System.IO.Path.Combine(sWebRootFolderNew, fileName + ".pdf"), Spire.Xls.FileFormat.PDF);
            FileInfo fospv = new FileInfo(Path.Combine(sWebRootFolderNew, fileName + ".pdf"));
            if (fospv.Exists)
            {
                return fospv.FullName;
            }
            return null;
        }
      

        public string ExportExcelNew(mPO po,int custlevel,string partialPP)
        {
            try
            {
                string sWebRootFolder = HostingEnvironment.MapPath("~/Content/templates/");
                string sWebRootFolderNew = HostingEnvironment.MapPath("~/uploads/doc/");
                string sWebRootFolderImg = HostingEnvironment.MapPath("~/uploads/");
                DirectoryInfo dir = new DirectoryInfo(sWebRootFolderNew);
                if (!dir.Exists)
                {
                    dir.Create();
                }
                string sFileName = @"template_nopp.xlsx";
                int count = po.customer.CustName.Split(' ').Length - 1;
                string newString;
                if (count > 1)
                {
                    newString = po.customer.CustName.Remove(po.customer.CustName.IndexOf(" ", po.customer.CustName.IndexOf(" ") + 1));
                }
                else
                {
                    newString = po.customer.CustName;
                }
                string regexSearch = new string(System.IO.Path.GetInvalidFileNameChars()) + new string(System.IO.Path.GetInvalidPathChars());
                Regex r = new Regex(string.Format("[{0}]", Regex.Escape(regexSearch)));
                newString = r.Replace(newString, "").Replace(".", "");
                string sFileNameNew;
                if (po.isPP)
                {
                    if (po.distBranch.TemplateEmailKhususAttach != null && !po.distBranch.TemplateEmailKhususAttach.Equals(""))
                    {
                        sFileName = po.distBranch.TemplateEmailKhususAttach;
                    }
                    else
                    {
                        //tambahan template pp khusus untuk HO
                        if(custlevel == 1)
                        {
                            sFileName = @"template_pp.xlsx";//nanti ganti template khusus ho
                        }
                        else
                        {
                            sFileName = @"template_pp.xlsx";
                        }
                    }
                    sFileNameNew = @"PP-" + newString + "-" + DateTime.Now.ToString("yyyyMMdd hmmsstt") + ".xlsx";
                }
                else
                {
                    if (po.distBranch.TemplateEmailRegularAttach != null && !po.distBranch.TemplateEmailRegularAttach.Equals(""))
                        sFileName = po.distBranch.TemplateEmailRegularAttach;
                    sFileNameNew = @"PO-" + newString + "-" + DateTime.Now.ToString("yyyyMMdd hmmsstt") + ".xlsx";
                }

                FileInfo newFile = new FileInfo(System.IO.Path.Combine(sWebRootFolderNew, sFileNameNew));
                FileInfo template = new FileInfo(System.IO.Path.Combine(sWebRootFolder, sFileName));

                using (ExcelPackage xlPackage = new ExcelPackage(newFile, template))
                {

                    ExcelWorksheet worksheetlampiran = xlPackage.Workbook.Worksheets["Lampiran PP"];
                    ExcelWorksheet worksheetpo = xlPackage.Workbook.Worksheets["PO"];
                    if (custlevel == 1)
                    {
                        worksheetpo.Cells[7, 3].Value = po.customer.CustName;
                        worksheetpo.Cells[8, 3].Value = po.customer.Address;
                        worksheetpo.Cells[8, 13].Value = po.PoId;
                    }
                    else
                    {
                        worksheetpo.Cells[7, 3].Value = po.customer.CustName;
                        worksheetpo.Cells[8, 3].Value = po.customer.Address;
                        worksheetpo.Cells[8, 13].Value = po.PoId;
                    }
                    

                    //ExcelCell cell;
                    ExcelWorksheet worksheet = xlPackage.Workbook.Worksheets["RowData"];
                    const int startRow = 2, startRowPo = 11, startRowLampiran = 18;
                    int rowTtl = 24;
                    int row = startRow;
                    int rowPo = startRowPo;
                    int rowLampiran = startRowLampiran;
                    int nomorurut = 1, rowttd = 53;
                   
                    double totalvalue = 0, totalvaluepp = 0, totalorder = 0, allbonus = 0, alldiskon = 0;
                    List<mPoLine> listbonus = new List<mPoLine>();
                    List<mPoLine> listitem = new List<mPoLine>();
                    foreach (mPoLine line in po.poLines)
                    {
                        if (line.Qty * line.UnitPrice != line.DiscRp)
                        {
                            listitem.Add(line);
                        }
                        else
                        {
                            line.Selected = false;
                            listbonus.Add(line);
                            allbonus += line.DiscRp;
                        }
                    }
                    //add item
                    if (listitem.Count > 0)
                    {
                        int mulaiItem = 0;
                        foreach (mPoLine line in listitem)
                        {
                            //untuk rowdata
                            if (row > startRow) worksheet.InsertRow(row, 1);
                            if (mulaiItem>=(rowTtl-rowPo))
                            {
                                worksheetpo.InsertRow(rowTtl, 1);
                                rowTtl++;
                            }

                            worksheet.Cells[row, 1].Value = po.distBranch.AreaCode;
                            worksheet.Cells[row, 2].Value = po.distBranch.CustCode;
                            worksheet.Cells[row, 3].Value = po.customer.CustName;
                            worksheet.Cells[row, 4].Value = line.PoId;
                            DateTime pDate = Convert.ToDateTime(po.PoDate);
                            worksheet.Cells[row, 5].Value = pDate.ToString("M/d/yyyy");
                            DateTime sDate = Convert.ToDateTime(po.ShipDate);
                            worksheet.Cells[row, 6].Value = sDate.ToString("M/d/yyyy");
                            worksheet.Cells[row, 7].Value = "Geisa";
                            worksheet.Cells[row, 8].Value = line.ProductCode;
                            worksheet.Cells[row, 9].Value = line.ProductId;
                            worksheet.Cells[row, 10].Value = line.ProductName;
                            worksheet.Cells[row, 11].Value = line.Qty;
                            worksheet.Cells[row, 12].Value = line.UnitId;
                            if (custlevel == 1)
                            {
                                worksheet.Cells[row, 13].Value = line.Disc1 / 100;
                                worksheet.Cells[row, 14].Value = line.Disc2 / 100;
                                worksheet.Cells[row, 15].Value = line.Disc3 / 100;
                                worksheet.Cells[row, 16].Value = po.Disc1 / 100;
                                worksheet.Cells[row, 17].Value = po.Disc2 / 100;
                                worksheet.Cells[row, 18].Value = (1 - (1 - line.Disc1 / 100) * (1 - line.Disc2 / 100) * (1 - line.Disc3 / 100) * (1 - po.Disc1/100) * (1 - po.Disc2/100));
                                double list= (1 - (1 - line.Disc1 / 100) * (1 - line.Disc2 / 100) * (1 - line.Disc3 / 100) * (1 - po.Disc1/100) * (1 - po.Disc2/100)); ;
                            }
                            //end row data

                            //untuk PO
                            if (custlevel == 1)
                            {
                                worksheetpo.Cells[rowPo, 2].Value = line.Qty;
                                worksheetpo.Cells[rowPo, 3].Value = line.UnitId;
                                worksheetpo.Cells[rowPo, 4].Value = line.ProductCode;
                                worksheetpo.Cells[rowPo, 5].Value = line.ProductName;
                                worksheetpo.Cells[rowPo, 9].Value = line.UnitPrice;
                                worksheetpo.Cells[rowPo, 10].Value = line.Disc1 / 100;
                                worksheetpo.Cells[rowPo, 11].Value = line.Disc2 / 100;
                                worksheetpo.Cells[rowPo, 12].Value = line.Disc3 / 100;
                            }
                            else
                            {
                                worksheetpo.Cells[rowPo, 2].Value = line.Qty;
                                worksheetpo.Cells[rowPo, 3].Value = line.UnitId;
                                worksheetpo.Cells[rowPo, 4].Value = line.ProductId;
                                worksheetpo.Cells[rowPo, 5].Value = line.ProductCode;
                                worksheetpo.Cells[rowPo, 6].Value = line.ProductName;
                                worksheetpo.Cells[rowPo, 10].Value = line.UnitPrice;
                                worksheetpo.Cells[rowPo, 11].Value = line.Disc1 / 100;
                                worksheetpo.Cells[rowPo, 12].Value = line.Disc2 / 100;
                            }
                            
                            double harga = 0;
                            if (line.IncludePPN)
                            {
                                harga = (line.UnitPrice / 1.1) * 1;
                            }
                            else
                            {
                                harga = line.UnitPrice;
                            }
                            double diskon = (1 - (1 - line.Disc1 / 100) * (1 - line.Disc2 / 100) *(1-line.Disc3/100));
                            double diskonpp = line.Disc1 / 100;
                            if (custlevel == 1)
                            {
                                //alldiskon += ((line.Qty * harga) * diskon) + line.DiscRp;
                            }
                            else
                            {
                                alldiskon += ((line.Qty * harga) * diskonpp) + line.DiscRp;
                            }
                            
                            totalorder += line.Qty * harga;
                            double itemvaluepp = line.Qty * (harga - (harga * diskonpp) - line.DiscRp);
                            double itemvalue = line.Qty * (harga - (harga * diskon) - line.DiscRp);
                            totalvaluepp += itemvaluepp;
                            totalvalue += itemvalue;
                            worksheetpo.Cells[rowPo, 13].Value = itemvalue;//nanti dicek lagi
                            row++;
                            rowPo++;

                            //add lampiran PP
                            if (po.isPP)
                            {
                                //untuk cek pp direct
                                if (custlevel == 1)
                                {
                                    worksheetlampiran.Cells[rowLampiran, 3].Value = nomorurut;
                                    worksheetlampiran.Cells[rowLampiran, 4].Value = line.ProductName;
                                    worksheetlampiran.Cells[rowLampiran, 6].Value = line.Qty;
                                    worksheetlampiran.Cells[rowLampiran, 7].Value = line.UnitId;
                                    worksheetlampiran.Cells[rowLampiran, 8].Value = line.UnitPrice;
                                    worksheetlampiran.Cells[rowLampiran, 9].Value = po.Disc1 / 100;
                                    worksheetlampiran.Cells[rowLampiran, 10].Value = po.Disc2 / 100;
                                    worksheetlampiran.Cells[rowLampiran, 11].Value = line.Disc1 / 100;
                                    worksheetlampiran.Cells[rowLampiran, 12].Value = line.Disc2 / 100;
                                    worksheetlampiran.Cells[rowLampiran, 13].Value = line.Disc3 / 100;
                                    double ttlpersen = (1 - (1 - line.Disc1 / 100) * (1 - line.Disc2 / 100) * (1 - line.Disc3 / 100) * (1 - po.Disc1/100) * (1 - po.Disc2/100));
                                    worksheetlampiran.Cells[rowLampiran, 14].Value = ttlpersen;
                                    if (line.poLineBonus != null)
                                    {
                                        worksheetlampiran.Cells[rowLampiran, 15].Value = line.poLineBonus.Qty;//bonus unit
                                    }
                                    else
                                    {
                                        worksheetlampiran.Cells[rowLampiran, 15].Value = 0;
                                    }


                                    if (line.Qty * line.UnitPrice == line.DiscRp)
                                    {
                                        worksheetlampiran.Cells[rowLampiran, 6].Value = 0;
                                        worksheetlampiran.Cells[rowLampiran, 15].Value = line.Qty;
                                        worksheetlampiran.Cells[rowLampiran, 16].Value = 0;
                                        worksheetlampiran.Cells[rowLampiran, 17].Value = line.DiscRp;
                                        worksheetlampiran.Cells[rowLampiran, 18].Value = 0;
                                        worksheetlampiran.Cells[rowLampiran, 19].Value = "Bonus";
                                    }
                                    else
                                    {
                                        worksheetlampiran.Cells[rowLampiran, 16].Value = worksheetlampiran.Calculate(worksheetlampiran.Cells[rowLampiran, 6] + "*" + worksheetlampiran.Cells[rowLampiran, 8]);
                                        worksheetlampiran.Cells[rowLampiran, 17].Value = worksheetlampiran.Calculate(worksheetlampiran.Cells[rowLampiran, 8] + "*" + worksheetlampiran.Cells[rowLampiran, 15]);
                                        worksheetlampiran.Cells[rowLampiran, 18].Value = worksheetlampiran.Calculate(worksheetlampiran.Cells[rowLampiran, 16] + "*" + worksheetlampiran.Cells[rowLampiran, 14]);

                                    }
                                    //add bonus
                                    foreach (mPoLine lb in listbonus)
                                    {
                                        if (line.ProductId.Equals(lb.ProductId) && line.UnitId.Equals(lb.UnitId) && line.ProductCode.Equals(lb.ProductCode) && !lb.Selected)
                                        {
                                            worksheetlampiran.Cells[rowLampiran, 15].Value = lb.Qty;
                                            worksheetlampiran.Cells[rowLampiran, 17].Value = worksheetlampiran.Calculate(worksheetlampiran.Cells[rowLampiran, 8] + "*" + worksheetlampiran.Cells[rowLampiran, 15]);
                                            lb.Selected = true;
                                            break;
                                        }
                                    }
                                    var valuedis = worksheetlampiran.Cells[rowLampiran, 18].Value;

                                   if (IsNumber(valuedis))
                                   {
                                        alldiskon += Double.Parse(valuedis.ToString());
                                   }
                                   
                                    
                                }
                                else
                                {
                                    worksheetlampiran.Cells[rowLampiran, 3].Value = nomorurut;
                                    worksheetlampiran.Cells[rowLampiran, 4].Value = line.ProductName;
                                    worksheetlampiran.Cells[rowLampiran, 6].Value = line.Qty;
                                    worksheetlampiran.Cells[rowLampiran, 7].Value = line.UnitId;
                                    worksheetlampiran.Cells[rowLampiran, 8].Value = line.UnitPrice;
                                    worksheetlampiran.Cells[rowLampiran, 9].Value = po.Disc2 / 100;
                                    worksheetlampiran.Cells[rowLampiran, 10].Value = po.CashDisc / 100;
                                    worksheetlampiran.Cells[rowLampiran, 11].Value = line.Disc2 / 100;
                                    if (line.poLineBonus != null)
                                    {
                                        worksheetlampiran.Cells[rowLampiran, 12].Value = line.poLineBonus.Qty;//bonus unit
                                    }
                                    else
                                    {
                                        worksheetlampiran.Cells[rowLampiran, 12].Value = 0;
                                    }
                                    worksheetlampiran.Cells[rowLampiran, 13].Value = po.Disc1 / 100;
                                    worksheetlampiran.Cells[rowLampiran, 14].Value = line.Disc1 / 100;


                                    if (line.Qty * line.UnitPrice == line.DiscRp)
                                    {
                                        worksheetlampiran.Cells[rowLampiran, 6].Value = 0;
                                        worksheetlampiran.Cells[rowLampiran, 12].Value = line.Qty;
                                        worksheetlampiran.Cells[rowLampiran, 15].Value = 0;
                                        worksheetlampiran.Cells[rowLampiran, 16].Value = line.DiscRp;
                                        worksheetlampiran.Cells[rowLampiran, 17].Value = 0;
                                    }
                                    else
                                    {
                                        worksheetlampiran.Cells[rowLampiran, 12].Value = 0;
                                        worksheetlampiran.Cells[rowLampiran, 15].Value = worksheetlampiran.Calculate(worksheetlampiran.Cells[rowLampiran, 6] + "*" + worksheetlampiran.Cells[rowLampiran, 8]);
                                        worksheetlampiran.Cells[rowLampiran, 16].Value = worksheetlampiran.Calculate(worksheetlampiran.Cells[rowLampiran, 8] + "*" + worksheetlampiran.Cells[rowLampiran, 12]);
                                        worksheetlampiran.Cells[rowLampiran, 17].Value = worksheetlampiran.Calculate("(" + worksheetlampiran.Cells[rowLampiran, 15] + "*(1-(1-" + worksheetlampiran.Cells[rowLampiran, 13] + ")*(1-" + worksheetlampiran.Cells[rowLampiran, 14] + ")))"); // = (O18 * (1 - (1 - M18) * (1 - N18)))"
                                    }
                                    //add bonus
                                    foreach (mPoLine lb in listbonus)
                                    {
                                        if (line.ProductId.Equals(lb.ProductId) && line.UnitId.Equals(lb.UnitId) && line.ProductCode.Equals(lb.ProductCode) && !lb.Selected)
                                        {
                                            worksheetlampiran.Cells[rowLampiran, 12].Value = lb.Qty;
                                            worksheetlampiran.Cells[rowLampiran, 16].Value = worksheetlampiran.Calculate(worksheetlampiran.Cells[rowLampiran, 8] + "*" + worksheetlampiran.Cells[rowLampiran, 12]);
                                            lb.Selected = true;
                                            break;
                                        }
                                    }
                                }
                                

                                nomorurut++;
                                rowLampiran++;
                                if (rowLampiran > 45)
                                    rowttd++;
                            }
                            mulaiItem++;
                        }
                    }

                    //add bonus
                    if (listbonus.Count > 0)
                    {
                        worksheetpo.Cells[rowPo, 2].Value = "Bonus Product";
                        rowPo++;
                        foreach (mPoLine line in listbonus)
                        {
                            //untuk rowdat
                            if (rowPo >= rowTtl)
                            {
                                worksheetpo.InsertRow(rowTtl, 1);
                                rowTtl++;
                            }
                            //untuk PO
                            if (custlevel == 1)
                            {
                                worksheetpo.Cells[rowPo, 2].Value = line.Qty;
                                worksheetpo.Cells[rowPo, 3].Value = line.UnitId;
                                worksheetpo.Cells[rowPo, 4].Value = line.ProductCode;
                                worksheetpo.Cells[rowPo, 5].Value = line.ProductName;
                            }
                            else
                            {
                                worksheetpo.Cells[rowPo, 2].Value = line.Qty;
                                worksheetpo.Cells[rowPo, 3].Value = line.UnitId;
                                worksheetpo.Cells[rowPo, 4].Value = line.ProductId;
                                worksheetpo.Cells[rowPo, 5].Value = line.ProductCode;
                                worksheetpo.Cells[rowPo, 6].Value = line.ProductName;
                            }
                           
                            rowPo++;
                            //add bonus di lampiran PP
                            if (po.isPP)
                            {
                                if (!line.Selected)
                                {
                                    if (custlevel == 1)
                                    {
                                        worksheetlampiran.Cells[rowLampiran, 3].Value = nomorurut;
                                        worksheetlampiran.Cells[rowLampiran, 4].Value = line.ProductName;
                                        worksheetlampiran.Cells[rowLampiran, 6].Value = line.Qty;
                                        worksheetlampiran.Cells[rowLampiran, 7].Value = line.UnitId;
                                        worksheetlampiran.Cells[rowLampiran, 8].Value = line.UnitPrice;
                                        worksheetlampiran.Cells[rowLampiran, 9].Value = po.Disc1 / 100;
                                        worksheetlampiran.Cells[rowLampiran, 10].Value = po.Disc2 / 100;
                                        worksheetlampiran.Cells[rowLampiran, 11].Value = line.Disc1 / 100;
                                        worksheetlampiran.Cells[rowLampiran, 12].Value = line.Disc2 / 100;
                                        worksheetlampiran.Cells[rowLampiran, 13].Value = line.Disc3 / 100;
                                        double ttlpersen = (1 - (1 - line.Disc1 / 100) * (1 - line.Disc2 / 100) * (1 - line.Disc3 / 100) * (1 - po.Disc1) * (1 - po.Disc2));
                                        worksheetlampiran.Cells[rowLampiran, 14].Value = ttlpersen;
                                        if (line.poLineBonus != null)
                                        {
                                            worksheetlampiran.Cells[rowLampiran, 15].Value = line.poLineBonus.Qty;//bonus unit
                                        }
                                        else
                                        {
                                            worksheetlampiran.Cells[rowLampiran, 15].Value = 0;
                                        }


                                        if (line.Qty * line.UnitPrice == line.DiscRp)
                                        {
                                            worksheetlampiran.Cells[rowLampiran, 6].Value = 0;
                                            worksheetlampiran.Cells[rowLampiran, 15].Value = line.Qty;
                                            worksheetlampiran.Cells[rowLampiran, 16].Value = 0;
                                            worksheetlampiran.Cells[rowLampiran, 17].Value = line.DiscRp;
                                            worksheetlampiran.Cells[rowLampiran, 18].Value = 0;
                                            worksheetlampiran.Cells[rowLampiran, 19].Value = "Bonus";
                                        }
                                        
                                    }
                                    else
                                    {
                                        worksheetlampiran.Cells[rowLampiran, 3].Value = nomorurut;
                                        worksheetlampiran.Cells[rowLampiran, 4].Value = line.ProductName;
                                        worksheetlampiran.Cells[rowLampiran, 6].Value = line.Qty;
                                        worksheetlampiran.Cells[rowLampiran, 7].Value = line.UnitId;
                                        worksheetlampiran.Cells[rowLampiran, 8].Value = line.UnitPrice;
                                        worksheetlampiran.Cells[rowLampiran, 9].Value = po.Disc2 / 100;
                                        worksheetlampiran.Cells[rowLampiran, 10].Value = po.CashDisc / 100;
                                        worksheetlampiran.Cells[rowLampiran, 11].Value = line.Disc2 / 100;
                                        if (line.poLineBonus != null)
                                        {
                                            worksheetlampiran.Cells[rowLampiran, 12].Value = line.poLineBonus.Qty;//bonus unit
                                        }
                                        else
                                        {
                                            worksheetlampiran.Cells[rowLampiran, 12].Value = 0;
                                        }
                                        worksheetlampiran.Cells[rowLampiran, 13].Value = po.Disc1 / 100;
                                        worksheetlampiran.Cells[rowLampiran, 14].Value = line.Disc1 / 100;


                                        if (line.Qty * line.UnitPrice == line.DiscRp)
                                        {
                                            worksheetlampiran.Cells[rowLampiran, 6].Value = 0;
                                            worksheetlampiran.Cells[rowLampiran, 12].Value = line.Qty;
                                            worksheetlampiran.Cells[rowLampiran, 15].Value = 0;
                                            worksheetlampiran.Cells[rowLampiran, 16].Value = line.DiscRp;
                                            worksheetlampiran.Cells[rowLampiran, 17].Value = 0;
                                        }
                                    }

                                    
                                    // worksheetlampiran.Cells[rowLampiran, 16].Value = worksheetlampiran.Calculate(worksheetlampiran.Cells[rowLampiran, 8] + "*" + worksheetlampiran.Cells[rowLampiran, 12]);

                                    nomorurut++;
                                    rowLampiran++;

                                    if (rowLampiran > 45)
                                        rowttd++;
                                }
                            }
                        }
                    }
                    //sheet PO
                    if (po.poLineOthers != null && po.poLineOthers.Count > 0)
                    {
                        worksheetpo.Cells[rowPo, 2].Value = "Other Product";
                        rowPo++;
                        //add po other
                        foreach (mPoLineOther line in po.poLineOthers)
                        {
                            if (rowPo >= rowTtl)
                            {
                                worksheetpo.InsertRow(rowTtl, 1);
                                rowTtl++;
                            }
                            if (custlevel == 1)
                            {
                                worksheetpo.Cells[rowPo, 2].Value = line.Qty;
                                worksheetpo.Cells[rowPo, 3].Value = line.Unit;
                                worksheetpo.Cells[rowPo, 4].Value = line.ProductCode;
                                worksheetpo.Cells[rowPo, 5].Value = line.ProductName;
                            }
                            else
                            {
                                worksheetpo.Cells[rowPo, 2].Value = line.Qty;
                                worksheetpo.Cells[rowPo, 3].Value = line.Unit;
                                worksheetpo.Cells[rowPo, 5].Value = line.ProductCode;
                                worksheetpo.Cells[rowPo, 6].Value = line.ProductName;
                            }
                           
                            rowPo++;
                        }
                    }
                    //untuk PO
                    rowTtl += 1;
                    double diskonheaderdist = 0 ;
                    if (custlevel == 1)
                    {
                        //check untuk direct
                        worksheetpo.Cells[rowTtl, 10].Value = po.Disc1 / 100;
                        worksheetpo.Cells[rowTtl, 11].Value = po.Disc2/100;
                        worksheetpo.Cells[rowTtl, 12].Value = po.CashDisc / 100;
                        double diskontotal = (1 - (1 - po.Disc1 / 100) * (1 - po.Disc2 / 100)*(1 - po.CashDisc/100));
                        //alldiskon += totalvaluepp * diskontotal;
                        double itemvaluetotal = totalvalue - (totalvalue * diskontotal);
                        double ppn = itemvaluetotal * 0.1;
                        double ttl = itemvaluetotal + ppn;
                        worksheetpo.Cells[rowTtl, 13].Value = itemvaluetotal;
                        worksheetpo.Cells[rowTtl + 1, 13].Value = ppn;
                        worksheetpo.Cells[rowTtl + 2, 13].Value = ttl;
                    }
                    else
                    {
                        diskonheaderdist = (1 - (1 - po.Disc2 / 100) * (1 - po.CashDisc / 100));
                        worksheetpo.Cells[rowTtl, 11].Value = po.Disc1 / 100;
                        worksheetpo.Cells[rowTtl, 12].Value = diskonheaderdist;
                        double diskontotal = (1 - (1 - po.Disc1 / 100) * (1 - diskonheaderdist));
                        double diskonppttl = po.Disc1 / 100;
                        alldiskon += totalvaluepp * diskonppttl;
                        double itemvaluetotal = totalvalue - (totalvalue * diskontotal);
                        double ppn = itemvaluetotal * 0.1;
                        double ttl = itemvaluetotal + ppn;
                        worksheetpo.Cells[rowTtl, 13].Value = itemvaluetotal;
                        worksheetpo.Cells[rowTtl + 1, 13].Value = ppn;
                        worksheetpo.Cells[rowTtl + 2, 13].Value = ttl;
                    }
                    
                    string notes="";
                    
                    if (po.CashDisc > 0)
                    {
                        if (custlevel == 1)
                        {
                            if(partialPP!=null)
                            notes = partialPP;
                        }
                        else
                        {
                            if (partialPP != null)
                            {
                                notes = partialPP + Environment.NewLine + "Total Disc 2 adalah " + diskonheaderdist + " % dengan Rincian" + Environment.NewLine +
                                        "Disc Distributor " + po.Disc2 + " % dan COD Diskon " + po.CashDisc + " %" + Environment.NewLine + po.Notes;
                            }
                            else
                            {
                                notes = "Total Disc 2 adalah " + diskonheaderdist + " % dengan Rincian" + Environment.NewLine +
                                        "Disc Distributor " + po.Disc2 + " % dan COD Diskon " + po.CashDisc + " %" + Environment.NewLine + po.Notes;
                            }
                        }
                        
                            
                        
                    }
                    else
                    {
                        if (partialPP != null)
                        {
                            notes = partialPP+ Environment.NewLine + po.Notes;
                        }else
                        {
                            notes = po.Notes;
                        }
                    }
                    worksheetpo.Cells[rowTtl + 1, 3].Value = notes;
                    worksheetpo.Cells[rowTtl + 4, 4].Value = po.ShipAddress;
                    DateTime oDate = Convert.ToDateTime(po.PoDate);
                    worksheetpo.Cells[rowTtl + 5, 11].Value = oDate.ToString("dd MMMM yyyy");
                    //add ttd
                    FileInfo fop = new FileInfo(System.IO.Path.Combine(sWebRootFolderImg, po.Signature));
                    if (fop.Exists)
                    {
                        System.Drawing.Image logo = System.Drawing.Image.FromFile(fop.FullName);
                        var picture = worksheetpo.Drawings.AddPicture(po.Signature + "PO", logo);
                        picture.SetSize(140, 85);
                        picture.SetPosition(rowTtl + 5, 0, 9, 35);
                    }
                    worksheetpo.Cells[rowTtl + 10, 11].Value = po.PicCust;
                    //sheet PP
                    if (po.isPP)
                    {
                        ExcelWorksheet worksheetPP = xlPackage.Workbook.Worksheets["PP"];
                        //nanti untuk pp ditambahkan
                        DateTime opDate = Convert.ToDateTime(po.PoDate);
                        DateTime spDate = Convert.ToDateTime(po.ShipDate);
                        DateTime onDate = Convert.ToDateTime(po.EndPeriodeDate);
                        worksheetPP.Cells[8, 4].Value = opDate.ToString("dd MMMM yyyy");
                        worksheetPP.Cells[9, 4].Value = po.distBranch.AreaName;
                        worksheetPP.Cells[10, 4].Value = po.PoId;
                        worksheetPP.Cells[9, 9].Value = po.PoId;
                        worksheetPP.Cells[13, 5].Value = po.PoId;
                        worksheetPP.Cells[14, 5].Value = po.customer.channel.ChannelName;
                        worksheetPP.Cells[15, 5].Value = opDate.ToString("dd MMMM yyyy") + "-" + onDate.ToString("dd MMMM yyyy");
                        worksheetPP.Cells[23, 5].Value = po.Mechanisme;
                        worksheetPP.Cells[37, 5].Value = po.distBranch.AreaName;
                        worksheetPP.Cells[38, 5].Value = po.customer.CustName;
                        //worksheetPP.Cells[41, 5].Value = itemvaluetotal;
                        worksheetPP.Cells[43, 6].Value = "All Product";
                        worksheetPP.Cells[43, 14].Value = totalorder;
                        worksheetPP.Cells[47, 6].Value = "All Bonus";
                        worksheetPP.Cells[47, 14].Value = allbonus;//bonus
                        worksheetPP.Cells[48, 6].Value = "All Diskon";
                        worksheetPP.Cells[48, 14].Value = alldiskon;//diskon

                        worksheetlampiran.Cells[3, 5].Value = opDate.ToString("dd MMMM yyyy");
                        worksheetlampiran.Cells[4, 5].Value = spDate.ToString("dd MMMM yyyy");
                        if (custlevel == 1)
                        {
                            worksheetlampiran.Cells[9, 9].Value = po.customer.CustName;
                            worksheetlampiran.Cells[10, 9].Value = po.customer.Address;
                            worksheetlampiran.Cells[12, 9].Value = po.ShipAddress;
                            worksheetlampiran.Cells[14, 9].Value = po.customer.Telp;
                        }
                        else
                        {
                            worksheetlampiran.Cells[9, 11].Value = po.customer.CustName;
                            worksheetlampiran.Cells[10, 11].Value = po.customer.Address;
                            worksheetlampiran.Cells[12, 11].Value = po.ShipAddress;
                            worksheetlampiran.Cells[14, 11].Value = po.customer.Telp;
                        }
                       



                        FileInfo fo = new FileInfo(System.IO.Path.Combine(sWebRootFolderImg, po.Signature));
                        if (fo.Exists)
                        {
                            System.Drawing.Image logo = System.Drawing.Image.FromFile(fo.FullName);
                            var picture = worksheetlampiran.Drawings.AddPicture(po.Signature, logo);
                            picture.SetSize(140, 105);
                            picture.SetPosition(rowttd, 0, 3, 0);
                        }
                        worksheetlampiran.Cells[rowttd + 6, 4].Value = po.PicCust;

                        //utk spv approval silahkan ditambahak dimari untuk tanda tangan
                        //if (po.sales.salesAtasan != null)
                        //{
                        //    if (po.sales.salesAtasan.SalesmanLevelId == 9 && po.sales.salesAtasan.AllowApproval)
                        //    {
                        //        FileInfo fospv = new FileInfo(Path.Combine(sWebRootFolderImg, po.sales.salesAtasan.SignatureName));
                        //        if (fospv.Exists)
                        //        {
                        //            Image logo = Image.FromFile(fospv.FullName);
                        //            var picture = worksheetlampiran.Drawings.AddPicture(po.Signature + "spv", logo);
                        //            picture.SetSize(140, 105);
                        //            picture.SetPosition(rowttd, 0, 13, 0);
                        //        }
                        //        worksheetlampiran.Cells[rowttd + 6, 14].Value = po.sales.salesAtasan.SalesmanName;
                        //    }else
                        //    {
                        //        if (po.sales.salesAtasan.salesAtasan != null)
                        //        {
                        //            if (po.sales.salesAtasan.salesAtasan.SalesmanLevelId < 9)
                        //            {
                        //                FileInfo fospv = new FileInfo(Path.Combine(sWebRootFolderImg, po.sales.salesAtasan.salesAtasan.SignatureName));
                        //                if (fospv.Exists)
                        //                {
                        //                    Image logo = Image.FromFile(fospv.FullName);
                        //                    var picture = worksheetlampiran.Drawings.AddPicture(po.Signature + "spv", logo);
                        //                    picture.SetSize(140, 105);
                        //                    picture.SetPosition(rowttd, 0, 13, 0);
                        //                }
                        //                worksheetlampiran.Cells[rowttd + 6, 14].Value = po.sales.salesAtasan.salesAtasan.SalesmanName;
                        //            }
                        //        }
                        //    }
                        //}
                        //tambahkan juga untuk PP mulai dari dbm,nbm,Cm dan Deputy GM


                    }
                    xlPackage.Save();
                }
                return newFile.FullName;
            }
            catch (Exception ex)
            {
                string e = ex.ToString();
                inserLog("ExportExcelNew", e, po.SalesmanId.ToString());
                return null;
            }

        }

        public static void ConvertToPdf(string input, string output)
        {
            //ExcelWorkbook wb = new ExcelWorkbook();
            //wb.LoadFromFile(input, ExcelVersion.Version2010);
            //wb.SaveToFile(output, FileFormat.PDF);
        }

        private bool IsNumber(object value)
        {
            return value is sbyte
                    || value is byte
                    || value is short
                    || value is ushort
                    || value is int
                    || value is uint
                    || value is long
                    || value is ulong
                    || value is float
                    || value is double
                    || value is decimal;
        }

    }
}