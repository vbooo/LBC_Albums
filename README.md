# LBC_Albums

Cette application affiche une liste d'albums (thumbnail + titre).

Les données, une fois récupérées, sont enregistrées sur une base de données locale. Si on perd la connexion réseau, alors l'application charge les donées enregistrées
en local. Si aucune données n'a été chargé en local (cas du premier lancement de l'appplication sans connexion réseau) alors un message d'erreur sera affiché pour
indiquer qu'aucun album n'est chargable. L'application gère l'affichage portrait + paysage.

Choix techniques:

L'architecture choisie est MVVM + clean architecture. Chacune des trois couches de la clean architecture est représentée par un module, permettant ainsi de respécter
au mieux les flux de dépendances entre les couches:
Presentation (module :app): contient Vue + ViewModel
Domain: contient les Model (classes métier) + UseCase
Data: contient les Repository + DataSource

La vue MainActivity, s'occupe uniquement d'afficher les données tenues par MainActivityViewModel.

L'avantage du ViewModel, au delà de gérer les données, est de gérer les changements de configuration, et ainsi de décharger la vue de la responsabilité du cycle de vie
(sur les changements d'orientation par exemple). Une couche supplémentaire a cependant été rajoutée entre MainActivity et MainActivityViewModel: MainActivityViewModelHelper.
Cette classe permet de gérer la logique liée à la vue, et de ne pas la mettre dans MainActivity. Celà permet de tester cette logique liée à l'UI pure.
MainActivityViewModel utilise les couroutines pour appeler le UseCase (GetAlbumsUseCase). Dans notre application, la pertinence du UseCase peut être discutable, car le but
principal de la couche domain, et donc du UseCase, est d'y mettre le code métier. Or, dans notre cas, nous n'avons pas de code métier car l'application est très simple 
dans ses fonctionnalités.

Le UseCase communique avec la couche Data via le principe d'inversion de dépendence (le D de SOLID). GetAlbumsUseCase appel le Repository via l'interface AlbumRepository.
De ce fait, le UseCase n'est pas lié directement à l'implémentation du Repository.

AlbumRepositoryImpl (qui correspond à l'implémentation de AlbumRepository, gère les différentes DataSource en fonction de la connectivité. S'il y a de la connexion 
réseau, alors on appel le webService pour récupérer les données (le client HTTP choisit est Retrofit). S'il n'y a pas de connexion, alors on charge les donées en local
s'il y en a (l'ORM choisit est Room).

Ainsi, les données sont collectées dans le ViewModel via les Flow de coroutine. Les LiveData présentes dans MainActivityViewModel sont mises à jour. MainActivity observe
ces LiveData, et met à jour sa vue en fonction de la valeur des données.

Pour l'injection de dépendence, HILT a été choisit.
