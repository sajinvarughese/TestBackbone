/**
 * New node file
 */
var instanceUrl;
var Client = Backbone.Model.extend({

	defaults : {

		name : null,

		email : null,

		pwd : null,

	},

	initialize : function() {

		console.log("initialize client");

	},

	url : "./register"

});

var ClientsCollection = Backbone.Collection.extend({

	model : Client

});

var ClientView = Backbone.View.extend({

				el : $("#divClient"), /* selecting the root element */
	
				initialize : function() {

				console.log("initializing View...");

				var that = this;

				this.listeClients = new ClientsCollection();

				this.listClients = new ClientsCollection();

				this.listeClients.bind("add", function(model) {

					that.addClientToList(model);

				});

				this.listClients.bind("add", function(model) {

					that.addLoginToList(model);

				});

			},

			events : {

				'click #cmdAddClient' : 'cmdAddClient_Click',

				'click #login' : 'login',

				'click #email' : 'removeErrorString',

				'click #password' : 'removeErrorString'

			},

			cmdAddClient_Click : function() {
				if ($("#username").val() == "" || $("#emailid").val() == ""
						|| $("#password").val() == ""
						|| $("#confirm-pass").val() == "") {

					alert("Fill All Fields");

				} else if ($("#password").val() != $("#confirm-pass").val()) {
					alert("password not matches");

				} else {

					var tmpClient = new Client({

						name : $("#username").val(),

						email : $("#emailid").val(),

						pwd : $("#password").val(),

					});

					this.listeClients.add(tmpClient);
				}
			},

			login : function() {

				var tmplogin = new Client({

					email : $("#email").val(),

					pwd : $("#password").val(),

				});

				this.listClients.add(tmplogin);

			},

			addClientToList : function(model) {

				var jsondata = JSON.stringify(model.toJSON());

				model.save({}, {
					success : function(model, response) {
						console.log('SUCCESS:');
						console.log(response);
						window.location = "login.html";
					},
					error : function(model, response) {
						console.log('FAIL:');
						console.log(response);
					}
				});

			},

			addLoginToList : function(model) {

				var jsondata = JSON.stringify(model.toJSON());
				model.save({}, {
					success : function(model, response) {
						console.log('SUCCESS:');
						console.log(response);
					},
					error : function(model, response) {
						console.log('FAIL:');
						console.log(response);
					}
				});

			},

			removeErrorString : function() {

				$("#listeClient").html("");
			}

		});

var clientView = new ClientView();

Backbone.history.start();
