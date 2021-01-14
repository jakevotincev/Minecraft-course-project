CREATE TABLE World (
                       id serial primary key,
                       name varchar NOT NULL
);

CREATE TABLE Caste (
                       id serial primary key,
                       name varchar NOT NULL,
                       hunger_ratio float NOT NULL DEFAULT 1.0
);

CREATE TABLE Settlement (
                            id serial primary key,
                            population int NOT NULL,
                            position_x int NOT NULL,
                            position_y int NOT NULL,
                            world_id int references World(id) NOT NULL,
                            caste_id int references Caste(id) NOT NULL
);

CREATE TABLE Food (
                      id serial primary key,
                      name varchar NOT NULL,
                      hp_regen float NOT NULL DEFAULT 1.0,
                      satiety float NOT NULL DEFAULT 1.0,
                      amount int NOT NULL DEFAULT 1,
                      settlement_id int references Settlement(id) NOT NULL
);

CREATE TABLE Resource (
                          id serial primary key,
                          amount int NOT NULL DEFAULT 1,
                          settlement_id int references Settlement(id) NOT NULL
);

CREATE TABLE People (
                        id serial primary key,
                        name varchar NOT NULL,
                        health float NOT NULL DEFAULT 100.0,
                        strength float NOT NULL DEFAULT 1.0,
                        money float NOT NULL DEFAULT 0.0,
                        is_pregnant boolean DEFAULT false,
                        gestational_age int DEFAULT -1,
                        settlement_id int references Settlement(id),
                        caste_id int references Caste(id) NOT NULL
);

CREATE TABLE Monsters (
                          id serial primary key,
                          name varchar NOT NULL,
                          health float NOT NULL DEFAULT 10.0,
                          damage float NOT NULL DEFAULT 0.5
);

CREATE TABLE Battle (
                        id serial primary key,
                        people_id int references People(id) NOT NULL,
                        monster_id int references Monsters(id) NOT NULL,
                        damage_to_person float NOT NULL DEFAULT 0.0,
                        damage_to_monster float NOT NULL DEFAULT 0.0
);

CREATE TABLE Animal (
                        id serial primary key,
                        name varchar NOT NULL,
                        health float NOT NULL DEFAULT 30.0,
                        damage float NOT NULL DEFAULT 1.0,
                        is_pregnant boolean DEFAULT false,
                        gestational_age float DEFAULT -1,
                        owner_id int references People(id)
);

CREATE TABLE Weapon (
                         id serial primary key,
                         name varchar NOT NULL DEFAULT 'Weapon',
                         strength float NOT NULL DEFAULT 1.0,
                         damage float NOT NULL DEFAULT 1.0,
                         owner_id int references People(id) NOT NULL
);

CREATE TABLE Enchantments (
                              id serial primary key,
                              name varchar NOT NULL DEFAULT 'Enchantment',
                              strength_bonus float NOT NULL DEFAULT 0.0,
                              damage_bonus float NOT NULL DEFAULT 0.0
);

CREATE TABLE Weapon_Enchantment (
                                    weapon_id      int references Weapon (id),
                                    enchantment_id int references Enchantments (id)
);

CREATE OR REPLACE PROCEDURE changeHealthAfterBattle(h_id int, m_id int, damage_p float, damage_m float) AS
$$
BEGIN
UPDATE People SET health = (health - damage_p) WHERE id = h_id;
UPDATE Monsters SET health = (health - damage_m) WHERE id = m_id;
END;
$$;

CREATE OR REPLACE PROCEDURE checkPersonHP(h_id int) AS
$$
BEGIN
DELETE FROM People WHERE id = h_id AND health < 0;
END;
$$;

CREATE OR REPLACE PROCEDURE checkMonsterHP(m_id int) AS
$$
BEGIN
DELETE FROM Monsters WHERE id = m_id AND health < 0;
end;
$$;

CREATE TRIGGER BattleTrigger AFTER INSERT ON Battle
    EXECUTE PROCEDURE changeHealthAfterBattle(new.people_id,
                                          new.monster_id,
                                          new.damage_to_person,
                                          new.damage_to_monster);

CREATE TRIGGER DeadPersonTrigger AFTER UPDATE ON People
    EXECUTE PROCEDURE checkPersonHP(new.id);

CREATE TRIGGER DeadMonsterTrigger AFTER UPDATE ON Monsters
    EXECUTE PROCEDURE checkMonsterHP(new.id);

create or replace function getAmountOfFoodSettlement(settlement integer) returns bigint as
$$ select sum(food.amount)
   from food
   where settlement_id = settlement $$
    language sql;


create or replace procedure increasePopulation(settlement text) as $$
declare count integer;
begin
select count(name) into count from people where people.settlement_id=settlement and  is_pregnant = true and gestational_age >3;
while count>0 loop
            insert into people(health, strength, money,is_pregnant, gestational_age, settlement_id) values (10, 2, 0, false, 0, settlement);
count:= count-1;
end loop;
end;
$$ language plpgsql;


create or replace function getAmountOfFoodCaste(integer) returns bigint as
$$
select sum(food.amount)
from settlement
         join food on settlement.id = food.settlement_id
where settlement.caste_id = $1;
$$
language sql;

create or replace procedure eatFood(s_id integer) as
$$
declare
ratio double precision;
    c_id integer;
begin
select caste_id into c_id from settlement where settlement.id = s_id;
select hunger_ratio into ratio from caste where caste.id = c_id;
update food set amount = amount - food.amount*ratio/10 where food.settlement_id = s_id;
end;
$$ language plpgsql;

create index settlement_id_index on settlement using hash(id);
create index settlement_c_id_index on settlement using hash (caste_id);
create index caste_id_index on caste using hash(id);
create index people_set_index on people using hash(settlement_id);
create index people_set_index on people using btree(gestational_age);
