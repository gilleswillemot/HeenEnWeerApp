using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.DependencyInjection;
using System.IO;
using G_HW_SL_PV_920.Data;
using G_HW_SL_PV_920.src.Data;
using G_HW_SL_PV_920.src.Data.Repositories;
using G_HW_SL_PV_920.src.Models.Domein;
using G_HW_SL_PV_920.src.Models.IRepository;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;

namespace G_HW_SL_PV_920
{
    public class Startup
    {

      public IConfigurationRoot Configuration { get; }


    // This method gets called by the runtime. Use this method to add services to the container.
    // For more information on how to configure your application, visit https://go.microsoft.com/fwlink/?LinkID=398940
    public void ConfigureServices(IServiceCollection services)
        {

          //DbContext for injection
          services.AddDbContext<ApplicationDbContext>(options => options.UseSqlServer("Data Source=(localdb)\\MSSQLLocalDB;Initial Catalog=HeenEnWeer;Integrated Security=True;Connect Timeout=30;Encrypt=False;TrustServerCertificate=True;ApplicationIntent=ReadWrite;MultiSubnetFailover=False"));
          services.AddScoped<IGebruikerRepository, GebruikerRepository>();
          services.AddScoped<IGezinRepository, GezinRepository>();
          services.AddScoped<IKalenderRepository, KalenderRepository>();
      
          services.AddTransient<DataInitializer>();
          services.AddIdentity<ApplicationUser, IdentityRole>(options =>
            {
              options.Password.RequiredLength = 5;
              options.Password.RequireNonAlphanumeric = false;
              options.Password.RequireDigit = false;
            })
            .AddEntityFrameworkStores<ApplicationDbContext>()
            .AddDefaultTokenProviders();
          services.AddMvc();
    }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env, DataInitializer ourDataInitializer)
        {
            //app.Use(async (context, next) => {
            //    await next();
            //    if (context.Response.StatusCode == 404 &&
            //       !Path.HasExtension(context.Request.Path.Value) &&
            //       !context.Request.Path.Value.StartsWith("/api/"))
            //    {
            //        context.Request.Path = "/app/registreer/registreer.component.html";
            //        await next();
            //    }
            //});
            app.UseMvcWithDefaultRoute();
            app.UseDefaultFiles();
            app.UseStaticFiles();
            app.UseAuthentication();
          ourDataInitializer.InitializeData();
         // ourDataInitializer.InitializeUsers();
        }
    }
}
