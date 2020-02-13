package com.dwh.genscripts.component;

import com.dwh.genscripts.dao.BaseDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class Startup {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private BaseDao baseDao;

    @PostConstruct
    public void afterInit(){
        this.baseDao = new BaseDao();
        genTemplate();
        genTemplateCtrl();
    }
    public void genTemplateCtrl(){
        List<String> tablesName = new ArrayList<>();
        tablesName.add("cvs_collateral_land");
        tablesName.add("cvs_collateral_building");
        tablesName.add("cvs_collateral_condo");
        tablesName.add("MARKET_BUILDING");
        tablesName.add("MARKET_CONDO");
        tablesName.add("MARKET_LAND");
        tablesName.add("MKT_COMPARISON_BUILDING");
        tablesName.add("MKT_COMPARISON_CONDO");
        tablesName.add("MKT_COMPARISON_LAND");
        tablesName.add("WQS");
        tablesName.add("WQS_LABEL");
        tablesName.add("WQS_REJECT_REASON");
        tablesName.add("WQS_SUMMARY");
        tablesName.add("WQS_TOTAL");
        tablesName.add("WQS_WEIGHT");

        try {
            for (String t : tablesName) {
                File log= new ClassPathResource("template_ctrl.sql").getFile();
                String sfilename = "'c:\\cvs\\DWH\\#filename#'";
                String rfilename = "'c:\\cvs\\DWH\\"+t+"_'";
                String sTableName = "#tablename#";  // <- changed to work with String.replaceAll()
                String rTableName = t;
                FileReader fr = new FileReader(log);


                String s;

                BufferedReader br = new BufferedReader(fr);
                StringBuilder tonewfile = new StringBuilder();
                while ((s = br.readLine()) != null) {
                    logger.info(s);
                    String result = StringUtils.replace(s, sTableName, rTableName );
                    result = StringUtils.replace(result, sfilename, rfilename );
                    logger.info(result);
                    // do something with the resulting line
                    tonewfile.append(result+" \n");
                }

                BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\output\\"+t+"_ctrl.sql"));
                writer.write(tonewfile.toString());

                writer.close();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void genTemplate(){

        List<String> tablesName = new ArrayList<>();
        tablesName.add("cvs_collateral_land");
        tablesName.add("cvs_collateral_building");
        tablesName.add("cvs_collateral_condo");
        tablesName.add("MARKET_BUILDING");
        tablesName.add("MARKET_CONDO");
        tablesName.add("MARKET_LAND");
        tablesName.add("MKT_COMPARISON_BUILDING");
        tablesName.add("MKT_COMPARISON_CONDO");
        tablesName.add("MKT_COMPARISON_LAND");
        tablesName.add("WQS");
        tablesName.add("WQS_LABEL");
        tablesName.add("WQS_REJECT_REASON");
        tablesName.add("WQS_SUMMARY");
        tablesName.add("WQS_TOTAL");
        tablesName.add("WQS_WEIGHT");

        try {
            for (String t : tablesName) {
                File log= new ClassPathResource("template.sql").getFile();
                String sfilename = "'c:\\cvs\\DWH\\#filename#'";
                String rfilename = "'c:\\cvs\\DWH\\"+t+"_'";
                String sTableName = "#tablename#";  // <- changed to work with String.replaceAll()
                String rTableName = t;
                FileReader fr = new FileReader(log);

                String ssql = "#selectstatement#";
                String rsql = baseDao.getSql(t);

                String s;

                BufferedReader br = new BufferedReader(fr);
                StringBuilder tonewfile = new StringBuilder();
                while ((s = br.readLine()) != null) {
                    logger.info(s);
                    String result = StringUtils.replace(s, sTableName, rTableName );
                    result = StringUtils.replace(result, ssql, rsql );
                    result = StringUtils.replace(result, sfilename, rfilename );
                    logger.info(result);
                    // do something with the resulting line
                    tonewfile.append(result+" \n");
                }

                BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\output\\"+t+".sql"));
                writer.write(tonewfile.toString());

                writer.close();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
