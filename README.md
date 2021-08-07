# Arkadia_ModModeration
Plugin ModModeration pour le serveur Arkadia

----- Config -----

Dans ce plugin vous avez plusieurs fichier de config :

- config.yml
- lang.yml
- message.yml

Le config.yml :

Le config permet de modifier si l'on activer les messages de mort, le chat custom et si l'on active les auto messages.
Il y as aussi la possibiliter d'activer et desactiver les messages de join et leave.

Le lang.yml :

Le lang permet de modifier tous les messages du plugin.

Les events :

- OnJoin
- OnLeave
- OnChat
- OnDead
- OnChangeDimension

Les commands :

  - mod:

    description: Permet de passez en ModModeration !
    
    aliases: ModModeration
    
    permission: modmoderation.mod
    
    #usage: Erreur de syntaxe ! Peut être que vous vouliez /mod ?

 - mtp:

    description: Permet de ce teleporter a un joueur !
    
    aliases: modTeleportation
    
    permission: modmoderation.mod
    
    #usage: Erreur de syntaxe ! Peut être que vous vouliez /mtp <joueur> ?

 - mtph:
  
    description: Permet de teleporter un joueur a nous !
  
    aliases: ModTeleportationHere
  
    permission: modmoderation.mod
  
    #usage: Erreur de syntaxe ! Peut être que vous vouliez /mtph <joueur> ?

 - verif:
  
    description: Permet de verif un joueur !
  
    aliases: verif
  
    permission: modmoderation.mod
  
    #usage: Erreur de syntaxe ! Peut être que vous vouliez /verif <joueur> ?

 - lookup:
  
    description: Permet de lookup un joueur !
  
    aliases: InformationJoueur
  
    permission: modmoderation.mod
  
    #usage: Erreur de syntaxe ! Peut être que vous vouliez /lookup <joueur> ?

 - discord:
  
    description: Affiche le discord !
  
    #aliases:
  
    #permission: modmoderation.mod
  
    #usage: Erreur de syntaxe ! Peut être que vous vouliez /discord ?

 - clearlag:
  
    description: Affiche le lag serveur !
  
    aliases: cl
  
    permission: modmoderation.clearlag
  
    #usage: Erreur de syntaxe ! Peut être que vous vouliez /clearlag ?

 - chatlock:
  
    description: Lock le chat !
  
    aliases: clock
  
    permission: modmoderation.chatlock
  
    #usage: Erreur de syntaxe ! Peut être que vous vouliez /chatlock ?

 - ModModerationReload:
  
    description: Reload le plugin !
  
    aliases: mmrl
  
    permission: modmoderation.reload
  
    #usage: Erreur de syntaxe ! Peut être que vous vouliez /modmoderationreload ?

 - modlist:
  
    description: List du staff en /mod !
  
    aliases: modl
  
    permission: modmoderation.mod
  
    #usage: Erreur de syntaxe ! Peut être que vous vouliez /modlist ?

 - staffchat:
  
    description: StaffChat !
  
    aliases: sc
  
    permission: modmoderation.staffchat
  
    #usage: Erreur de syntaxe ! Peut être que vous vouliez /staffchat ?

 - nightvision:
  
    description: Avoir l'effect de potion NightVision !
  
    aliases: ngvstop
  
    permission: modmoderation.nightvision
  
    #usage: Erreur de syntaxe ! Peut être que vous vouliez /nightvision ?

 - gg:
    description: GamemodeChange !
  
    aliases: gamemodechange
  
    permission: modmoderation.gamemodechange
  
    #usage: Erreur de syntaxe ! Peut être que vous vouliez /gg ?

Pour plus d'information : https://docs.google.com/presentation/d/1HE54LacqOPkZGvZRnkme4cIpPmvIW0P__gxJ1WpPxfA/edit?usp=sharing
