INSERT INTO public.clients (guid, agency, created_datetime, dob, first_name, last_name, status)
VALUES ('01588E84-D45A-EB98-F47F-716073A4F1EF', 'vhh4', '2021-11-15 11:51:59',
        '10-15-1999', 'Ne', 'Abr', 'INACTIVE');

INSERT INTO public.notes (guid, client_guid, comments, created_datetime, date_time, logged_user, modified_datetime)
VALUES ('20CBCEDA-3764-7F20-0BB6-4D6DD46BA9F8', 'C5DCAA49-ADE5-E65C-B776-3F6D7B5F2055',
        'Patient Care Coordinator, reached out to patient caregiver is still in the hospital.',
        '2021-11-15 11:51:59', '2021-09-16 12:02:26 CDT', 'p.vasya',
        '2021-11-15 11:51:59');