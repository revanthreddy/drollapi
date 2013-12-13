create database ideas;

use ideas;

CREATE TABLE `tb_domains` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `description` varchar(64) default '',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1  ;


CREATE TABLE `tb_ideas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` mediumtext NOT NULL,
  `user_name` varchar(64) DEFAULT '',
`public` tinyint(1) DEFAULT '1',
    `domain_id` int(11) NULL,
    `open` boolean,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (`id`),
CONSTRAINT  FOREIGN KEY (`domain_id`) REFERENCES `tb_domains` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1  ;


CREATE TABLE `tb_comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` mediumtext NOT NULL,
  `idea_id` int(11),
    `open` boolean,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (`id`),
CONSTRAINT  FOREIGN KEY (`idea_id`) REFERENCES `tb_ideas` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1  ;

alter table tb_ideas add `public` boolean default 1 after user_name  ;

alter table tb_ideas add `like_counter` int(11) default 0 after `public` ;

alter table tb_comments add `user_name` varchar(64) ;


insert into tb_domains values(0 , 'Technology','');
insert into tb_domains values(0 , 'Utilities','');
insert into tb_domains values(0 , 'Entertainment','');
insert into tb_domains values(0 , 'Others','');