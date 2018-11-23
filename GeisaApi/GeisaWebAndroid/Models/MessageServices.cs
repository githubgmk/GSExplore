using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Net;
using System.Net.Mail;
using System.Threading.Tasks;
using System.Text.RegularExpressions;
using System.Net.Mime;
using System.IO;
using GeisaWebAndroid.ProsesDB;

namespace GeisaWebAndroid.Models
{
    public class MessageServices
    {
        public async static Task SendEmailAsync(string sender, string sendername, List<MailAddress> email, List<MailAddress> emailcc, string subject, string message, AlternateView signature, List<String> attachment)
        {
            try
            {
                // var _email = "system.geisaforce@gmail.com"; //"simisol@outlook.com";
                // var _epass = "geisa2015";
                var _email = "system.geisaforce@gandummas.co.id"; //"simisol@outlook.com";
                var _epass = "R0b0c@rp0l1";
                var _dispName = "System GeisaForce";
                var mailsender = _email;
                var mailsendername = _dispName;
                if (IsEmailValid(sender))
                    mailsender = sender;
                if (sendername != null && sendername != "")
                    mailsendername = sendername;

                MailMessage myMessage = new MailMessage();
                foreach (MailAddress ma in email)
                {
                    myMessage.To.Add(ma);
                }
                foreach (MailAddress ma in emailcc)
                {
                    myMessage.CC.Add(ma);
                }
                myMessage.Bcc.Add(new MailAddress("system.geisaforce@gandummas.co.id", "system.geisaforce@gandummas.co.id"));
                //myMessage.Bcc.Add(new MailAddress("andi@gandummas.co.id", "andi@gandummas.co.id"));
                //myMessage.Bcc.Add(new MailAddress("panji.anom@gandummas.co.id", "panji.anom@gandummas.co.id"));
                //myMessage.Bcc.Add(new MailAddress("yulis@gandummas.co.id", "yulis@gandummas.co.id"));
                myMessage.From = new MailAddress(mailsender, mailsendername);
                myMessage.Subject = subject;
                myMessage.Body = message;
                if (attachment != null && attachment.Count > 0)
                {
                    foreach (String s in attachment)
                    {
                        try
                        {
                            if (!s.Equals("") || s == null) // 6/5/2018 ---- set pointer null
                            {
                                Attachment attach = new Attachment(s);
                                myMessage.Attachments.Add(attach);
                            }
                        }
                        catch (Exception ex)
                        {
                            spLog log = new spLog();
                            log.inserLog("attach file", ex.ToString(), subject);
                        }

                    }
                }
                myMessage.IsBodyHtml = true;


                if (signature != null)
                {
                    myMessage.AlternateViews.Add(signature);
                }

                using (SmtpClient smtp = new SmtpClient())
                {
                    smtp.EnableSsl = true;
                    // smtp.Host = "smtp.gmail.com";  // "smtp.live.com";
                    // smtp.Port = 587;
                    smtp.Host = "mail.gandummas.co.id";  // "smtp.live.com";
                    smtp.Port = 587;
                    smtp.UseDefaultCredentials = true;
                    smtp.Credentials = new NetworkCredential(_email, _epass);
                    smtp.DeliveryMethod = SmtpDeliveryMethod.Network;
                    smtp.SendCompleted += (s, e) => { smtp.Dispose(); };
                    await smtp.SendMailAsync(myMessage);
                }
            }
            catch (Exception ex)
            {
                spLog log = new spLog();
                log.inserLog("SendEmailAsync", ex.ToString(), subject);
                throw ex;
            }


        }

        public static Boolean IsEmailValid(string EmailAddr)
        {

            if (EmailAddr != null || EmailAddr != "")
            {

                Regex n = new Regex("(?<user>[^@]+)@(?<host>.+)");
                Match v = n.Match(EmailAddr);

                if (!v.Success || EmailAddr.Length != v.Length)
                {

                    return false;
                }

                else

                {

                    return true;
                }

            }

            else

            {

                return false;
            }

        }
    }
}