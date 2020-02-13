set pagesize 0;
set linesize 2000;
set trimspool on;
set feed off;
set headsep off;
set time off
set echo off
set serveroutput on
set verify off
/* Generate name of Interface File .................................. */
col iFile new_value iFile
select
       'c:\cvs\DWH\#filename#'
              || TO_CHAR(TRUNC(SYSDATE), 'YYYY-MM-DD')
              || '_000000.000000.dat' iFile
from
       dual
;

spool &iFile
SELECT
       #selectstatement#
FROM
       #tablename#
WHERE
       (
              CREATED_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE) + INTERVAL '1' DAY - INTERVAL '1' SECOND
       )
       OR
       (
              UPDATED_DATE BETWEEN TRUNC(SYSDATE) AND TRUNC(SYSDATE) + INTERVAL '1' DAY - INTERVAL '1' SECOND
       )
;

spool off
EXIT