set pagesize 0;
set linesize 20000;
set trimspool on;
set feed off;
set headsep off;
set time off
set echo off
set serveroutput on
set verify off
set long 20000;
set longchunksize 20000;

/* Generate name of Interface File .................................. */
col iFile new_value iFile
select '&2/'||#filename#|| TO_CHAR(TO_DATE('&1','YYYY-MM-DD')-1, 'YYYY-MM-DD') || '_000000.000000.ctl' iFile from dual;
spool &iFile

    SELECT TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') || '.000000' ||
       TO_CHAR(TO_DATE('&1','YYYY-MM-DD')-1, 'YYYY-MM-DD') || ' 00:00:00.000000' ||
       TO_CHAR(TO_DATE('&1','YYYY-MM-DD')-1, 'YYYY-MM-DD') || ' 23:59:59.999999' || '0000000001' || LPAD(COUNT(*), 10, '0')
  FROM #tablename#
 WHERE (CREATED_DATE >= TRUNC(TO_DATE('&1','YYYY-MM-DD')-1)
   AND CREATED_DATE < TRUNC(TO_DATE('&1','YYYY-MM-DD')-1) + INTERVAL '1' DAY)
    OR (UPDATED_DATE >= TRUNC(TO_DATE('&1','YYYY-MM-DD')-1)
   AND UPDATED_DATE < TRUNC(TO_DATE('&1','YYYY-MM-DD')-1) + INTERVAL '1' DAY);


spool off

EXIT