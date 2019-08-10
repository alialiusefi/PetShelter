use petshelter;
create index petId_ix on pets (id);
create index petName_idx on pets (name);
create index breedID_idx on pets (breed_id);
create index shelterID_idx on pets (shelter_id);
create index petdateofbirth_idx on pets (dateofbirth);

create index userID_idx on users (id);
create index adoptionID_idx on adoptions_made (id);
