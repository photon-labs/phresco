<?php
/**
 * The base configurations of the WordPress.
 *
 * This file has the following configurations: MySQL settings, Table Prefix,
 * Secret Keys, WordPress Language, and ABSPATH. You can find more information
 * by visiting {@link http://codex.wordpress.org/Editing_wp-config.php Editing
 * wp-config.php} Codex page. You can get the MySQL settings from your web host.
 *
 * This file is used by the wp-config.php creation script during the
 * installation. You don't have to use the web site, you can just copy this file
 * to "wp-config.php" and fill in the values.
 *
 * @package WordPress
 */

// ** MySQL settings - You can get this info from your web host ** //
/** The name of the database for WordPress */
include('config/config.php');
$currentEnv = getenv('SERVER_ENVIRONMENT');
$type = "Database";
$name = "";

$properties = getConfigByName($currentEnv, $type, $name);
//$properties = parse_ini_file('config.ini', true);
define('DB_NAME', $properties[0]->dbname);

/** MySQL database username */
define('DB_USER', $properties[0]->username);

/** MySQL database password */
define('DB_PASSWORD', $properties[0]->password);

/** MySQL hostname */
define('DB_HOST', $properties[0]->host);

/** Database Charset to use in creating database tables. */
define('DB_CHARSET', 'utf8');

/** The Database Collate type. Don't change this if in doubt. */
define('DB_COLLATE', '');

/**#@+
 * Authentication Unique Keys and Salts.
 *
 * Change these to different unique phrases!
 * You can generate these using the {@link https://api.wordpress.org/secret-key/1.1/salt/ WordPress.org secret-key service}
 * You can change these at any point in time to invalidate all existing cookies. This will force all users to have to log in again.
 *
 * @since 2.6.0
 */
define('AUTH_KEY',         'Z[pi1ds&a.-T|7{iSsyz:e#ti)r2<{{)l<AX4[TS@FOmQ|mDdH87>0RUPO~_4*}A');
define('SECURE_AUTH_KEY',  'qXl-axLx8?Ir,G[7em!i+Ob`T$Rv_RVs$cQ0:Fk*GR[4RlTP%MH0n-5Ruty%lp*k');
define('LOGGED_IN_KEY',    'z)}5^+.0w^3]SWY!9`i8nsvjDWmk`PoV<B65>jgiXMAuX`vN-<lsGKj1O8W&}&1F');
define('NONCE_KEY',        'g2jw-)}CWnKPc~7:WX*6~Z8K[y{MeM ,{=Ot|xWLz?QPRJ.Mog*1zN{*F4UYyI<8');
define('AUTH_SALT',        's4z!&?{:U6I%m:Qy`A_ft/?#&%Asw[rH@qU)/7Ju:lt>P>VEN<9`YCOMIsqL.^*v');
define('SECURE_AUTH_SALT', 'jC%<[D62=X>g8Z8 _!6F3i^EgmR}k,t1hoTnKWj]4m^mj+jUDHW;oKD/rW4iP+?6');
define('LOGGED_IN_SALT',   '*P_]CR7OAF,up`?<pNp[yE!rN/.CQbB~cnfDrEJ(!Y4YY)!$cSO,ljC?|Sr,|&Vi');
define('NONCE_SALT',       'V8TM?fOU#B!]mLhF=m!O?qV Zy7t?vYor*/z0]&MF{NZ;*]X_lImrNAW_>BYRV5|');

/**#@-*/

/**
 * WordPress Database Table prefix.
 *
 * You can have multiple installations in one database if you give each a unique
 * prefix. Only numbers, letters, and underscores please!
 */
$table_prefix  = 'wp_';

/**
 * WordPress Localized Language, defaults to English.
 *
 * Change this to localize WordPress. A corresponding MO file for the chosen
 * language must be installed to wp-content/languages. For example, install
 * de_DE.mo to wp-content/languages and set WPLANG to 'de_DE' to enable German
 * language support.
 */
define('WPLANG', '');

/**
 * For developers: WordPress debugging mode.
 *
 * Change this to true to enable the display of notices during development.
 * It is strongly recommended that plugin and theme developers use WP_DEBUG
 * in their development environments.
 */
define('WP_DEBUG', false);

/* That's all, stop editing! Happy blogging. */

/** Absolute path to the WordPress directory. */
if ( !defined('ABSPATH') )
	define('ABSPATH', dirname(__FILE__) . '/');

/** Sets up WordPress vars and included files. */
require_once(ABSPATH . 'wp-settings.php');
