/*
   Copyright 2011 tSQLt

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
EXEC sp_configure 'clr enabled', 1;
RECONFIGURE;
EXEC sp_configure 'show advanced option', '1';
RECONFIGURE;
EXEC sp_configure 'clr strict security', 0;
RECONFIGURE;
EXEC sp_configure 'show advanced option', '0';
RECONFIGURE;
--GO
--DECLARE @cmd NVARCHAR(MAX);
--SET @cmd='ALTER DATABASE ' + QUOTENAME(DB_NAME()) + ' SET TRUSTWORTHY ON;';
--EXEC(@cmd);
--GO
/*CREATE or ALTER ASSEMBLY for assembly 'tSQLtCLR' with the SAFE or EXTERNAL_ACCESS option failed 
because the 'clr strict security' option of sp_configure is set to 1. 
Microsoft recommends that you sign the assembly with a certificate or asymmetric key 
that has a corresponding login with UNSAFE ASSEMBLY permission. 
Alternatively, you can trust the assembly using sp_add_trusted_assembly.*/