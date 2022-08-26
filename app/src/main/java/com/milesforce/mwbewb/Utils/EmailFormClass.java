package com.milesforce.mwbewb.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.widget.AppCompatSpinner;

import com.milesforce.mwbewb.R;

import java.util.ArrayList;

public class EmailFormClass {
    static WindowManager.LayoutParams lp;
    EditText emails_to, email_subject, emails_cc, email_bcc, email_message_body;
    AppCompatSpinner template_spinner;
    ArrayList<String> templatesArrayList;
    Context context;
    String TemplateOne = "<body class=\"clean-body\" style=\"margin: 0; padding: 0; -webkit-text-size-adjust: 100%; background-color: #D4E1F7;\">\n" +
            "<!--[if IE]><div class=\"ie-browser\"><![endif]-->\n" +
            "<table bgcolor=\"#D4E1F7\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; min-width: 320px; Margin: 0 auto; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #D4E1F7; width: 100%;\" valign=\"top\" width=\"100%\">\n" +
            "<tbody>\n" +
            "<tr style=\"vertical-align: top;\" valign=\"top\">\n" +
            "<td style=\"word-break: break-word; vertical-align: top; border-collapse: collapse;\" valign=\"top\">\n" +
            "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color:#D4E1F7\"><![endif]-->\n" +
            "<div style=\"background-color:#D4E1F7;\">\n" +
            "<div class=\"block-grid\" data-body-width-father=\"875px\" rel=\"col-num-container-box-father\" style=\"Margin: 0 auto; min-width: 320px; max-width: 875px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FAFAFA;\">\n" +
            "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FAFAFA;\">\n" +
            "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:#D4E1F7;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:875px\"><tr class=\"layout-full-width\" style=\"background-color:#FAFAFA\"><![endif]-->\n" +
            "<!--[if (mso)|(IE)]><td align=\"center\" width=\"875\" style=\"background-color:#FAFAFA;width:875px; border-top: 1px solid transparent; border-left: 1px solid transparent; border-bottom: 1px solid transparent; border-right: 1px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;background-color:#D4E1F7;\"><![endif]-->\n" +
            "<div class=\"col num12\" data-body-width-son=\"873\" rel=\"col-num-container-box-son\" style=\"min-width: 320px; max-width: 875px; display: table-cell; vertical-align: top;\">\n" +
            "<div style=\"background-color:#D4E1F7;width:100% !important;\">\n" +
            "<!--[if (!mso)&(!IE)]><!-->\n" +
            "<div style=\"border-top:1px solid transparent; border-left:1px solid transparent; border-bottom:1px solid transparent; border-right:1px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
            "<!--<![endif]-->\n" +
            "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 0px; padding-bottom: 10px; font-family: 'Times New Roman', Georgia, serif\"><![endif]-->\n" +
            "<div style=\"color:#555555;font-family:TimesNewRoman, 'Times New Roman', Times, Beskerville, Georgia, serif;line-height:120%;padding-top:0px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" +
            "<div style=\"font-family: TimesNewRoman, 'Times New Roman', Times, Beskerville, Georgia, serif; font-size: 12px; line-height: 14px; color: #555555;\">\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"><br/></p>\n" +
            "</div>\n" +
            "</div>\n" +
            "<!--[if mso]></td></tr></table><![endif]-->\n" +
            "<!--[if (!mso)&(!IE)]><!-->\n" +
            "</div>\n" +
            "<!--<![endif]-->\n" +
            "</div>\n" +
            "</div>\n" +
            "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
            "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div style=\"background-color:transparent;\">\n" +
            "<div class=\"block-grid\" data-body-width-father=\"875px\" rel=\"col-num-container-box-father\" style=\"Margin: 0 auto; min-width: 320px; max-width: 875px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FAFAFA;\">\n" +
            "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FAFAFA;\">\n" +
            "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:875px\"><tr class=\"layout-full-width\" style=\"background-color:#FAFAFA\"><![endif]-->\n" +
            "<!--[if (mso)|(IE)]><td align=\"center\" width=\"875\" style=\"background-color:#FAFAFA;width:875px; border-top: 1px solid #CBC8C8; border-left: 1px solid #CBC8C8; border-bottom: 0px solid transparent; border-right: 1px solid #CBC8C8;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
            "<div class=\"col num12\" data-body-width-son=\"873\" rel=\"col-num-container-box-son\" style=\"min-width: 320px; max-width: 875px; display: table-cell; vertical-align: top;\">\n" +
            "<div style=\"width:100% !important;\">\n" +
            "<!--[if (!mso)&(!IE)]><!-->\n" +
            "<div style=\"border-top:1px solid #CBC8C8; border-left:1px solid #CBC8C8; border-bottom:0px solid transparent; border-right:1px solid #CBC8C8; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
            "<!--<![endif]-->\n" +
            "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: 'Times New Roman', Georgia, serif\"><![endif]-->\n" +
            "<div style=\"color:#555555;font-family:TimesNewRoman, 'Times New Roman', Times, Beskerville, Georgia, serif;line-height:120%;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" +
            "<div style=\"font-family: TimesNewRoman, 'Times New Roman', Times, Beskerville, Georgia, serif; font-size: 12px; line-height: 14px; color: #555555;\">\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"><strong>Dear Vanitha,</strong></p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">Greetings from Miles!</p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">Please find below key highlights of US CPA &amp; CMA.</p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"><strong>Certified Public Accountant (CPA)</strong></p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">    <strong>✓</strong>  US equivalent of Indian CA</p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">    <strong>✓</strong>  12 months course</p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">    <strong>✓</strong>  Only 4 exams | Covered over 28 Sunday classes</p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">    <strong>✓</strong>  Exams in Dubai or US; may be held in India from 2018</p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">    <strong>✓</strong>  @Miles,India’s largest CPA review,\"Registered Course Provider\" with the AICPA,US support also includes CPA license &amp; placement with Big 4 &amp;  MNCs.</p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">         <a href=\"[weblink]\" rel=\"noopener\" style=\"color: #0068A5;\" target=\"_blank\">Read More</a></p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"><strong>Certified Management Accountant (CMA) </strong></p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">    <strong>✓</strong>  US equivalent of Indian CMA [earlier CWA]</p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">    <strong>✓</strong>  6 months Course</p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">    <strong>✓</strong>  Only 2 exams | Covered over 11 Sunday Classes</p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">    <strong>✓</strong>  Exams in India</p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">    <strong>✓</strong>  @Miles,IMA’s “official” India partner learn using \"official\" IMA study materials published by Wiley avail of IMA-Wiley bundle discounts and get placed MNCs.</p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">         <a href=\"[weblink]\" rel=\"noopener\" style=\"color: #0068A5;\" target=\"_blank\">Read More</a></p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">Give us a shout for any further info, we’d be happy to be of assistance. Looking forward to having you on-board soon as we sail towards destination CPA/CMA.</p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
            "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">Cheers!</p>\n" +
            "</div>\n" +
            "</div>\n" +
            "<!--[if mso]></td></tr></table><![endif]-->\n" +
            "<!--[if (!mso)&(!IE)]><!-->\n" +
            "</div>\n" +
            "<!--<![endif]-->\n" +
            "</div>\n" +
            "</div>\n" +
            "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
            "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div style=\"background-color:transparent;\">\n" +
            "<div class=\"block-grid\" data-body-width-father=\"875px\" rel=\"col-num-container-box-father\" style=\"Margin: 0 auto; min-width: 320px; max-width: 875px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FAFAFA;\">\n" +
            "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FAFAFA;\">\n" +
            "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:875px\"><tr class=\"layout-full-width\" style=\"background-color:#FAFAFA\"><![endif]-->\n" +
            "<!--[if (mso)|(IE)]><td align=\"center\" width=\"875\" style=\"background-color:#FAFAFA;width:875px; border-top: 0px solid transparent; border-left: 1px solid #CBC8C8; border-bottom: 0px solid transparent; border-right: 1px solid #CBC8C8;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
            "<div class=\"col num12\" data-body-width-son=\"873\" rel=\"col-num-container-box-son\" style=\"min-width: 320px; max-width: 875px; display: table-cell; vertical-align: top;\">\n" +
            "<div style=\"width:100% !important;\">\n" +
            "<!--[if (!mso)&(!IE)]><!-->\n" +
            "<div style=\"border-top:0px solid transparent; border-left:1px solid #CBC8C8; border-bottom:0px solid transparent; border-right:1px solid #CBC8C8; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
            "<!--<![endif]-->\n" +
            "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\" width=\"100%\">\n" +
            "<tbody>\n" +
            "<tr style=\"vertical-align: top;\" valign=\"top\">\n" +
            "<td class=\"divider_inner\" style=\"word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px; border-collapse: collapse;\" valign=\"top\">\n" +
            "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider_content\" height=\"0\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; border-top: 1px solid #BBBBBB; height: 0px;\" valign=\"top\" width=\"100%\">\n" +
            "<tbody>\n" +
            "<tr style=\"vertical-align: top;\" valign=\"top\">\n" +
            "<td height=\"0\" style=\"word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; border-collapse: collapse;\" valign=\"top\"><span></span></td>\n" +
            "</tr>\n" +
            "</tbody>\n" +
            "</table>\n" +
            "</td>\n" +
            "</tr>\n" +
            "</tbody>\n" +
            "</table>\n" +
            "<!--[if (!mso)&(!IE)]><!-->\n" +
            "</div>\n" +
            "<!--<![endif]-->\n" +
            "</div>\n" +
            "</div>\n" +
            "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
            "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div style=\"background-color:transparent;\">\n" +
            "<div class=\"block-grid\" data-body-width-father=\"875px\" rel=\"col-num-container-box-father\" style=\"Margin: 0 auto; min-width: 320px; max-width: 875px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FAFAFA;\">\n" +
            "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FAFAFA;\">\n" +
            "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:875px\"><tr class=\"layout-full-width\" style=\"background-color:#FAFAFA\"><![endif]-->\n" +
            "<!--[if (mso)|(IE)]><td align=\"center\" width=\"875\" style=\"background-color:#FAFAFA;width:875px; border-top: 0px solid transparent; border-left: 1px solid #CBC8C8; border-bottom: 1px solid #CBC8C8; border-right: 1px solid #CBC8C8;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
            "<div class=\"col num12\" data-body-width-son=\"873\" rel=\"col-num-container-box-son\" style=\"min-width: 320px; max-width: 875px; display: table-cell; vertical-align: top;\">\n" +
            "<div style=\"width:100% !important;\">\n" +
            "<!--[if (!mso)&(!IE)]><!-->\n" +
            "<div style=\"border-top:0px solid transparent; border-left:1px solid #CBC8C8; border-bottom:1px solid #CBC8C8; border-right:1px solid #CBC8C8; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
            "<!--<![endif]-->\n" +
            "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: 'Times New Roman', Georgia, serif\"><![endif]-->\n" +
            "<div style=\"color:#555555;font-family:TimesNewRoman, 'Times New Roman', Times, Beskerville, Georgia, serif;line-height:120%;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" +
            "<div style=\"font-family: TimesNewRoman, 'Times New Roman', Times, Beskerville, Georgia, serif; font-size: 12px; line-height: 14px; color: #555555;\">\n" +
            "<p style=\"font-size: 14px; line-height: 12px; text-align: center; margin: 0;\"><span style=\"font-size: 10px;\">2017 © Miles Professional Education Private Limited. </span><br/><span style=\"font-size: 10px; line-height: 12px;\">Add our address to your Safe Sender list to ensure delivery.</span><br/><span style=\"font-size: 10px; line-height: 12px;\">Not interested? <a href=\"[weblink]\" rel=\"noopener\" style=\"color: #0068A5;\" target=\"_blank\">Click here</a>  to unsubscribe</span><br/><span style=\"font-size: 10px; line-height: 12px;\">Please send any comments about this email to <a href=\"[weblink]\" rel=\"noopener\" style=\"color: #0068A5;\" target=\"_blank\">support@</a> </span></p>\n" +
            "</div>\n" +
            "</div>\n" +
            "<!--[if mso]></td></tr></table><![endif]-->\n" +
            "<!--[if (!mso)&(!IE)]><!-->\n" +
            "</div>\n" +
            "<!--<![endif]-->\n" +
            "</div>\n" +
            "</div>\n" +
            "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
            "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div style=\"background-color:#D4E1F7;\">\n" +
            "<div class=\"block-grid\" data-body-width-father=\"875px\" rel=\"col-num-container-box-father\" style=\"Margin: 0 auto; min-width: 320px; max-width: 875px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FAFAFA;\">\n" +
            "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FAFAFA;\">\n" +
            "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:#D4E1F7;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:875px\"><tr class=\"layout-full-width\" style=\"background-color:#FAFAFA\"><![endif]-->\n" +
            "<!--[if (mso)|(IE)]><td align=\"center\" width=\"875\" style=\"background-color:#FAFAFA;width:875px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;background-color:#D4E1F7;\"><![endif]-->\n" +
            "<div class=\"col num12\" data-body-width-son=\"875\" rel=\"col-num-container-box-son\" style=\"min-width: 320px; max-width: 875px; display: table-cell; vertical-align: top;\">\n" +
            "<div style=\"background-color:#D4E1F7;width:100% !important;\">\n" +
            "<!--[if (!mso)&(!IE)]><!-->\n" +
            "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
            "<!--<![endif]-->\n" +
            "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: 'Times New Roman', Georgia, serif\"><![endif]-->\n" +
            "<div style=\"color:#555555;font-family:TimesNewRoman, 'Times New Roman', Times, Beskerville, Georgia, serif;line-height:120%;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" +
            "<div style=\"font-family: TimesNewRoman, 'Times New Roman', Times, Beskerville, Georgia, serif; font-size: 12px; line-height: 14px; color: #555555;\">\n" +
            "<p style=\"font-size: 14px; line-height: 16px; margin: 0;\"> </p>\n" +
            "</div>\n" +
            "</div>\n" +
            "<!--[if mso]></td></tr></table><![endif]-->\n" +
            "<!--[if (!mso)&(!IE)]><!-->\n" +
            "</div>\n" +
            "<!--<![endif]-->\n" +
            "</div>\n" +
            "</div>\n" +
            "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
            "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>\n" +
            "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
            "</td>\n" +
            "</tr>\n" +
            "</tbody>\n" +
            "</table>\n" +
            "<!--[if (IE)]></div><![endif]-->\n" +
            "</body>";


    String two = "<h2 style=\" margin-bottom:30px; line-height: 1.5;font-weight:600;\"><a target=\"_blank\" href=\"https://www.mileseducation.com/cma/Training-Videos\" style=\"color#3e4d5c\">\n" +
            "    <strong class=\"national-title\">Introducing the world's favorite CPA &amp; CMA instructor - <br style=\" display:block; \"></strong>Varun\n" +
            "    Jain,&nbsp;<span style=\"font-size:16px;\">CPA, CMA,\n" +
            "                                Harvard B-School\n" +
            "                                Alumnus</span> </a></h2>\n" +
            "<img src=\"https://assets.mileseducation.com/images/MilesEducation_Logo.png\">";

    public void SendNewEMailsToClient(int userId, String Emails, String AccessToken, Context context) {
        getTemplateData();
        this.context = context;
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.main_email_form);
        dialog.setCancelable(true);
        emails_to = dialog.findViewById(R.id.emails_to);
        email_subject = dialog.findViewById(R.id.email_subject);
        emails_cc = dialog.findViewById(R.id.emails_cc);
        email_bcc = dialog.findViewById(R.id.email_bcc);
        email_message_body = dialog.findViewById(R.id.email_message_body);
        emails_to.setText(Emails);
        template_spinner = dialog.findViewById(R.id.template_spinner);
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, templatesArrayList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        template_spinner.setAdapter(dataAdapter);
        template_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (template_spinner.getSelectedItem().toString().equals("Custom Template")) {
                    email_message_body.setText("");
                }
                if (template_spinner.getSelectedItem().toString().equals("Template 1")) {
                    email_message_body.setText(Html.fromHtml(two, new ImageGetter(), null));
                } else {
                    email_message_body.setText(Html.fromHtml(TemplateOne));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setAttributes(lp);
    }

    private void getTemplateData() {
        templatesArrayList = new ArrayList<>();
        templatesArrayList.add("Custom Template");
        templatesArrayList.add("Road Map Template");
        templatesArrayList.add("Template 1");
        templatesArrayList.add("Template 2");
        templatesArrayList.add("Template 3");
        templatesArrayList.add("Template 4");
        templatesArrayList.add("Template 5");
    }

    private class ImageGetter implements Html.ImageGetter {

        public Drawable getDrawable(String source) {
            int id;

            id = context.getResources().getIdentifier(source, "drawable", context.getPackageName());

            if (id == 0) {
                // the drawable resource wasn't found in our package, maybe it is a stock android drawable?
                id = context.getResources().getIdentifier(source, "drawable", "android");
            }

            if (id == 0) {
                // prevent a crash if the resource still can't be found
                return null;
            } else {
                Drawable d = context.getResources().getDrawable(id);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                return d;
            }
        }

    }
}
