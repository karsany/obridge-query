select z.*,                    Cursor (Select z.id || 'ABCD' || rownum As description, :test as sub_id
                             From dual
                           Connect By rownum < 4) As embedded_object from (
Select id,
       'HELLO' || rownum As name,
       Sysdate As current_date, '2011-11-11' from_text_date
  From (select rownum as id from dual
Connect By rownum < 11) q) z  where name like :pattern