<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">




<div class="clear"></div>
<!-- 各家模式预报24 -->
<div class="btitle" style="text-align: center">24小时各家预报结果</div>
<div id="all">
	<c:forEach items="${map}" var="entry">
		
		<div id="${entry.key}24" class="hour3" >
			<div class="row first">
				<div class="label">${entry.key}</div>
				<c:forEach items="${entry.value}" var="forecast">
					<div style="font-size: 12px;">${forecast.forecastType }</div>
				</c:forEach>
			</div>
			<div class="row second tqxx">
				<div class="label h3_tqxx">天气现象</div>
				<c:forEach items="${entry.value}" var="forecast">
					<c:choose>
						<c:when test="${hour==20}">
							<div>
								<img src="../style/img/1${forecast.skyDay }.png">～<img
									src="../style/img/1${forecast.skyNight}.png">
							</div>
						</c:when>
						<c:when test="${hour==8}">
							<div>
								<img src="../style/img/1${forecast.skyNight }.png">～<img
									src="../style/img/1${forecast.skyDay}.png">
							</div>
						</c:when>
						<c:otherwise>
							<div></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<div class="row wd">
				<div class="label h3_wd">气温</div>
				<c:forEach items="${entry.value}" var="forecast" varStatus="s">
					<c:choose>
						<c:when test="${hour==20}">
							<div>${forecast.maxTemp }～${forecast.minTemp }℃</div>
						</c:when>
						<c:when test="${hour==8}">
							<div>${forecast.minTemp }～${forecast.maxTemp }℃</div>
						</c:when>
						<c:otherwise>
							<div></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<div class="row js">
				<div class="label h3_js">降水（白天/夜间）</div>
				<c:forEach items="${entry.value}" var="forecast" varStatus="s">
					<c:choose>
						<c:when test="${hour==20}">
							<div>${forecast.rainDay }～${forecast.rainNight }</div>
						</c:when>
						<c:when test="${hour==8}">
							<div>${forecast.rainNight }～${forecast.rainDay }</div>
						</c:when>
						<c:otherwise>
							<div></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<div class="row winds">
				<div class="label h3_fl">风力</div>
				<c:forEach items="${entry.value}" var="forecast" varStatus="s">
					<c:choose>
						<c:when test="${hour==20}">
							<div>${forecast.windVelocityDay }～${forecast.windVelocityNight }</div>
						</c:when>
						<c:when test="${hour==8}">
							<div>${forecast.windVelocityNight }～${forecast.windVelocityNight}</div>
						</c:when>
						<c:otherwise>
							<div></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<div class="row windd">
				<div class="label h3_fx">风向</div>
				<c:forEach items="${entry.value}" var="forecast" varStatus="s">
					<c:choose>
						<c:when test="${hour==20}">
							<div>${forecast.windDirectionDay }～${forecast.windDirectionNight }</div>
						</c:when>
						<c:when test="${hour==8}">
							<div>${forecast.windDirectionNight }～${forecast.windDirectionDay}</div>
						</c:when>
						<c:otherwise>
							<div></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<div class="row xdsd">
				<div class="label h3_xdsd">相对湿度</div>
				<c:forEach items="${entry.value}" var="forecast" varStatus="s">
					<c:choose>
						<c:when test="${hour==20}">
							<div>${forecast.RHDay }～${forecast.RHNight }</div>
						</c:when>
						<c:when test="${hour==8}">
							<div>${forecast.RHNight }～${forecast.RHDay}</div>
						</c:when>
						<c:otherwise>
							<div></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
		</div>
	</c:forEach>
</div>

<!-- 各家模式预报48 -->

<div class="btitle" style="text-align: center">48小时各家预报结果</div>
<div id="all">
	<c:forEach items="${map48}" var="entry">
		<div id="${entry.key}48" class="hour3" >
			<div class="row first">
				<div class="label">${entry.key}</div>
				<c:forEach items="${entry.value}" var="forecast">
					<div style="font-size: 12px;">${forecast.forecastType }</div>
				</c:forEach>
			</div>
			<div class="row second tqxx">
				<div class="label h3_tqxx">天气现象</div>
				<c:forEach items="${entry.value}" var="forecast">
					<c:choose>
						<c:when test="${hour==20}">
							<div>
								<img src="../style/img/1${forecast.skyDay }.png">～<img
									src="../style/img/1${forecast.skyNight}.png">
							</div>
						</c:when>
						<c:when test="${hour==8}">
							<div>
								<img src="../style/img/1${forecast.skyNight }.png">～<img
									src="../style/img/1${forecast.skyDay}.png">
							</div>
						</c:when>
						<c:otherwise>
							<div></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<div class="row wd">
				<div class="label h3_wd">气温</div>
				<c:forEach items="${entry.value}" var="forecast" varStatus="s">
					<c:choose>
						<c:when test="${hour==20}">
							<div>${forecast.maxTemp }～${forecast.minTemp }℃</div>
						</c:when>
						<c:when test="${hour==8}">
							<div>${forecast.minTemp }～${forecast.maxTemp }℃</div>
						</c:when>
						<c:otherwise>
							<div></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<div class="row js">
				<div class="label h3_js">降水（白天/夜间）</div>
				<c:forEach items="${entry.value}" var="forecast" varStatus="s">
					<c:choose>
						<c:when test="${hour==20}">
							<div>${forecast.rainDay }～${forecast.rainNight }</div>
						</c:when>
						<c:when test="${hour==8}">
							<div>${forecast.rainNight }～${forecast.rainDay }</div>
						</c:when>
						<c:otherwise>
							<div></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<div class="row winds">
				<div class="label h3_fl">风力</div>
				<c:forEach items="${entry.value}" var="forecast" varStatus="s">
					<c:choose>
						<c:when test="${hour==20}">
							<div>${forecast.windVelocityDay }～${forecast.windVelocityNight }</div>
						</c:when>
						<c:when test="${hour==8}">
							<div>${forecast.windVelocityNight }～${forecast.windVelocityNight}</div>
						</c:when>
						<c:otherwise>
							<div></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<div class="row windd">
				<div class="label h3_fx">风向</div>
				<c:forEach items="${entry.value}" var="forecast" varStatus="s">
					<c:choose>
						<c:when test="${hour==20}">
							<div>${forecast.windDirectionDay }～${forecast.windDirectionNight }</div>
						</c:when>
						<c:when test="${hour==8}">
							<div>${forecast.windDirectionNight }～${forecast.windDirectionDay}</div>
						</c:when>
						<c:otherwise>
							<div></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<div class="row xdsd">
				<div class="label h3_xdsd">相对湿度</div>
				<c:forEach items="${entry.value}" var="forecast" varStatus="s">
					<c:choose>
						<c:when test="${hour==20}">
							<div>${forecast.RHDay }～${forecast.RHNight }</div>
						</c:when>
						<c:when test="${hour==8}">
							<div>${forecast.RHNight }～${forecast.RHDay}</div>
						</c:when>
						<c:otherwise>
							<div></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
		</div>
	</c:forEach>
</div>


<!-- 各家模式预报72 -->

<div class="btitle" style="text-align: center">72小时各家预报结果</div>
<div id="all">
	<c:forEach items="${map72}" var="entry">
		<div id="${entry.key}72" class="hour3" >
			<div class="row first">
				<div class="label">${entry.key}</div>
				<c:forEach items="${entry.value}" var="forecast">
					<div style="font-size: 12px;">${forecast.forecastType }</div>
				</c:forEach>
			</div>
			<div class="row second tqxx">
				<div class="label h3_tqxx">天气现象</div>
				<c:forEach items="${entry.value}" var="forecast">
					<c:choose>
						<c:when test="${hour==20}">
							<div>
								<img src="../style/img/1${forecast.skyDay }.png">～<img
									src="../style/img/1${forecast.skyNight}.png">
							</div>
						</c:when>
						<c:when test="${hour==8}">
							<div>
								<img src="../style/img/1${forecast.skyNight }.png">～<img
									src="../style/img/1${forecast.skyDay}.png">
							</div>
						</c:when>
						<c:otherwise>
							<div></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<div class="row wd">
				<div class="label h3_wd">气温</div>
				<c:forEach items="${entry.value}" var="forecast" varStatus="s">
					<c:choose>
						<c:when test="${hour==20}">
							<div>${forecast.maxTemp }～${forecast.minTemp }℃</div>
						</c:when>
						<c:when test="${hour==8}">
							<div>${forecast.minTemp }～${forecast.maxTemp }℃</div>
						</c:when>
						<c:otherwise>
							<div></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<div class="row js">
				<div class="label h3_js">降水（白天/夜间）</div>
				<c:forEach items="${entry.value}" var="forecast" varStatus="s">
					<c:choose>
						<c:when test="${hour==20}">
							<div>${forecast.rainDay }～${forecast.rainNight }</div>
						</c:when>
						<c:when test="${hour==8}">
							<div>${forecast.rainNight }～${forecast.rainDay }</div>
						</c:when>
						<c:otherwise>
							<div></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<div class="row winds">
				<div class="label h3_fl">风力</div>
				<c:forEach items="${entry.value}" var="forecast" varStatus="s">
					<c:choose>
						<c:when test="${hour==20}">
							<div>${forecast.windVelocityDay }～${forecast.windVelocityNight }</div>
						</c:when>
						<c:when test="${hour==8}">
							<div>${forecast.windVelocityNight }～${forecast.windVelocityNight}</div>
						</c:when>
						<c:otherwise>
							<div></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<div class="row windd">
				<div class="label h3_fx">风向</div>
				<c:forEach items="${entry.value}" var="forecast" varStatus="s">
					<c:choose>
						<c:when test="${hour==20}">
							<div>${forecast.windDirectionDay }～${forecast.windDirectionNight }</div>
						</c:when>
						<c:when test="${hour==8}">
							<div>${forecast.windDirectionNight }～${forecast.windDirectionDay}</div>
						</c:when>
						<c:otherwise>
							<div></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<div class="row xdsd">
				<div class="label h3_xdsd">相对湿度</div>
				<c:forEach items="${entry.value}" var="forecast" varStatus="s">
					<c:choose>
						<c:when test="${hour==20}">
							<div>${forecast.RHDay }～${forecast.RHNight }</div>
						</c:when>
						<c:when test="${hour==8}">
							<div>${forecast.RHNight }～${forecast.RHDay}</div>
						</c:when>
						<c:otherwise>
							<div></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
		</div>
	</c:forEach>
</div>


<div class="btitle" style="text-align: center">各家温度预报趋势</div>
<div>
	<c:forEach items="${forecasts7DayMax}" var="entry">
		<div class="clear"></div>
		<br>
		<br>
		<div id="${entry.key}7DayMax" class='7Day'
			style="width: 1200px; height: 400px; margin: 0 auto; "></div>
		<script language="JavaScript">
					$(function() { 
						
						var title = {
					      text: '${entry.key}最高气温趋势'   
					   };
					   var subtitle = {
					      text: ''
					   };
					   var xAxis = {
					      categories: ${bfDays}
					   };
					   var yAxis = {
					      title: {
					         text: 'Temperature (\xB0C)'
					      },
					      labels: {
					          formatter: function () {
					             return this.value + '\xB0';
					          }
					       },
					       lineWidth: 2
					   };
					   var plotOptions = {
					      line: {
					         dataLabels: {
					            enabled: true
					         },   
					         enableMouseTracking: true
					      },
					      spline: {
					          marker: {
					             radius: 4,
					             lineWidth: 1
					          }
					       }
					   };
					   var tooltip = {
							      crosshairs: true,
							      shared: true
							   };
					   var series= [
						<c:forEach items="${entry.value}" var="en">
						         {
						         name: '${en.key}',
						         data: ${en.value}
						      },
					      </c:forEach>
					   ];
					   
					   var json = {};
					   json.title = title;
					   json.subtitle = subtitle;
					   json.tooltip = tooltip;
					   json.xAxis = xAxis;
					   json.yAxis = yAxis;  
					   json.series = series;
					   json.plotOptions = plotOptions;
					   $('#${entry.key}7DayMax').highcharts(json);
					});
					</script>
	</c:forEach>
</div>


<br>

<div>
	<c:forEach items="${forecasts7DayMin}" var="entry">
		<div class="clear"></div>
		<br>
		<br>
		<div id="${entry.key}7DayMin" class='7Day'
			style="width: 1200px; height: 400px; margin: 0 auto; "></div>
		<script language="JavaScript">
					$(function() { 
						
						var title = {
					      text: '${entry.key}最低气温趋势'   
					   };
					   var subtitle = {
					      text: ''
					   };
					   var xAxis = {
					      categories: ${bfDays}
					   };
					   var yAxis = {
					      title: {
					         text: 'Temperature (\xB0C)'
					      },
					      labels: {
					          formatter: function () {
					             return this.value + '\xB0';
					          }
					       },
					       lineWidth: 2
					   };
					   var plotOptions = {
					      line: {
					         dataLabels: {
					            enabled: true
					         },   
					         enableMouseTracking: true
					      },
					      spline: {
					          marker: {
					             radius: 4,
					             lineWidth: 1
					          }
					       }
					   };
					   var tooltip = {
							      crosshairs: true,
							      shared: true
							   };
					   var series= [
						<c:forEach items="${entry.value}" var="en">
						         {
						         name: '${en.key}',
						         data: ${en.value}
						      },
					      </c:forEach>
					   ];
					   
					   var json = {};
					   json.title = title;
					   json.subtitle = subtitle;
					   json.tooltip = tooltip;
					   json.xAxis = xAxis;
					   json.yAxis = yAxis;  
					   json.series = series;
					   json.plotOptions = plotOptions;
					   $('#${entry.key}7DayMin').highcharts(json);
					});
					</script>
	</c:forEach>
</div>

<div class="btitle" style="text-align: center">各家温度预报正确率统计</div>
<div>
	<c:forEach items="${deviationMax2}" var="entry">
		<div class="clear"></div>
		<div id="${entry.key}deviationMax2" class='max2'
			style="width: 605px; height: 400px; margin: 5; float: left;"></div>
		<div id="${entry.key}deviationMin2" class='max2'
			style="width: 605px; height: 400px; margin: 5; float: left;"></div>

		<script language="JavaScript">
					$(function() { 
						var chart = {
				            plotBackgroundColor: null,
				            plotBorderWidth: null,
				            plotShadow: false
				        };
				        var title = {
				            text: '${entry.key}最近七天最高气温2度正确率统计'
				        };
				        var tooltip = {
				            pointFormat: '{series.name}: <b>{point.y}</b>'
				        };
				        var plotOptions = {
				            pie: {
				                allowPointSelect: true,
				                cursor: 'pointer',
				                dataLabels: {
				                    enabled: true,
				                    format: '<b>{point.name}</b>: {point.y}',
				                    style: {
				                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
				                    }
				                }
				            }
				        };
				        var series = [{
				            type: 'pie',
				            name: '正确个数',
				            data: [
								<c:forEach items="${entry.value}" var="en">
									['${en.key}',${en.value}],
									</c:forEach>
				            ],
				        }];
					   var json = {};
					   json.chart = chart;
					   json.tooltip = tooltip;
					   json.plotOptions = plotOptions;
					   json.title = title;
					   json.series = series;
					   $('#${entry.key}deviationMax2').highcharts(json);
					});
					</script>
	</c:forEach>

	<br>
	<c:forEach items="${deviationMin2}" var="entry">
		<script language="JavaScript">
					$(function() { 
						var chart = {
				            plotBackgroundColor: null,
				            plotBorderWidth: null,
				            plotShadow: false
				        };
				        var title = {
				            text: '${entry.key}最近七天最低气温2度正确率统计'
				        };
				        var tooltip = {
				            pointFormat: '{series.name}: <b>{point.y}</b>'
				        };
				        var plotOptions = {
				            pie: {
				                allowPointSelect: true,
				                cursor: 'pointer',
				                dataLabels: {
				                    enabled: true,
				                    format: '<b>{point.name}</b>: {point.y}',
				                    style: {
				                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
				                    }
				                }
				            }
				        };
				        var series = [{
				            type: 'pie',
				            name: '正确个数',
				            data: [
								<c:forEach items="${entry.value}" var="en">
									['${en.key}',${en.value}],
									</c:forEach>
				            ],
				        }];
					   var json = {};
					   json.chart = chart;
					   json.tooltip = tooltip;
					   json.plotOptions = plotOptions;
					   json.title = title;
					   json.series = series;
					   $('#${entry.key}deviationMin2').highcharts(json);
					});
					</script>
	</c:forEach>


	<c:forEach items="${deviationMax1}" var="entry">
		<div class="clear"></div>
		<div style="float: center">
			<div id="${entry.key}deviationMax1" class='max2'
				style="width: 605px; height: 400px; margin: 5; float: left; "></div>
			<div id="${entry.key}deviationMin1" class='max2'
				style="width: 605px; height: 400px; margin: 5; float: left; "></div>
		</div>
		<script language="JavaScript">
					$(function() {
						var chart = {
				            plotBackgroundColor: null,
				            plotBorderWidth: null,
				            plotShadow: false
				        };
				        var title = {
				            text: '${entry.key}最近七天最高气温1度正确率统计'
				        };
				        var tooltip = {
				            pointFormat: '{series.name}: <b>{point.y}</b>'
				        };
				        var plotOptions = {
				            pie: {
				                allowPointSelect: true,
				                cursor: 'pointer',
				                dataLabels: {
				                    enabled: true,
				                    format: '<b>{point.name}</b>: {point.y}',
				                    style: {
				                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
				                    }
				                }
				            }
				        };
				        var series = [{
				            type: 'pie',
				            name: '正确个数',
				            data: [
								<c:forEach items="${entry.value}" var="en">
									['${en.key}',${en.value}],
									</c:forEach>
				            ],
				        }];
					   var json = {};
					   json.chart = chart;
					   json.tooltip = tooltip;
					   json.plotOptions = plotOptions;
					   json.title = title;
					   json.series = series;
					   $('#${entry.key}deviationMax1').highcharts(json);
					});
					</script>
	</c:forEach>
</div>

<div>
	<c:forEach items="${deviationMin1}" var="entry">
		<script language="JavaScript">
					$(function() { 
						var chart = {
				            plotBackgroundColor: null,
				            plotBorderWidth: null,
				            plotShadow: false
				        };
				        var title = {
				            text: '${entry.key}最近七天最低气温1度正确率统计'
				        };
				        var tooltip = {
				            pointFormat: '{series.name}: <b>{point.y}</b>'
				        };
				        var plotOptions = {
				            pie: {
				                allowPointSelect: true,
				                cursor: 'pointer',
				                dataLabels: {
				                    enabled: true,
				                    format: '<b>{point.name}</b>: {point.y}',
				                    style: {
				                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
				                    }
				                }
				            }
				        };
				        var series = [{
				            type: 'pie',
				            name: '正确个数',
				            data: [
								<c:forEach items="${entry.value}" var="en">
									['${en.key}',${en.value}],
									</c:forEach>
				            ],
				        }];
					   var json = {};
					   json.chart = chart;
					   json.tooltip = tooltip;
					   json.plotOptions = plotOptions;
					   json.title = title;
					   json.series = series;
					   $('#${entry.key}deviationMin1').highcharts(json);
					});
					</script>
	</c:forEach>
</div>

<div class="btitle" style="text-align: center">各家温度预报误差</div>

<div>
	<c:forEach items="${deviationMax}" var="entry">
		<div class="clear"></div>
		<br>
		<br>
		<div id="${entry.key}deviationMax" class='6Day'
			style="width: 1200px; height: 400px; margin: 0 auto;"></div>
		<script language="JavaScript">
					$(function() { 
						var chart= {
				            type: 'column'
				        };
						var title = {
					      text: '${entry.key}最近7天最高气温误差'   
					   };
				        var xAxis= {
				            categories: ${bf7Days}
				        };
				        var credits= {
				            enabled: false
				        };
					   var series= [
						<c:forEach items="${entry.value}" var="en">
						         {
						         name: '${en.key}',
						         data: ${en.value}
						      },
					      </c:forEach>
					   ];
					   var json = {};
					   json.chart = chart;
					   json.xAxis = xAxis;
					   json.credits = credits;
					   json.title = title;
					   json.series = series;
					   $('#${entry.key}deviationMax').highcharts(json);
					});
					</script>
	</c:forEach>
</div>

<br>
<div>
	<c:forEach items="${deviationMin}" var="entry">
		<div class="clear"></div>
		<br>
		<br>
		<div id="${entry.key}deviationMin" class='6Day'
			style="width: 1200px; height: 400px; margin: 0 auto; "></div>
		<script language="JavaScript">
					$(function() { 
						var chart= {
				            type: 'column'
				        };
						var title = {
					      text: '${entry.key}最近7天最低气温误差'   
					   };
				        var xAxis= {
				        		categories: ${bf7Days}
				        };
				        var credits= {
				            enabled: false
				        };
					   var series= [
						<c:forEach items="${entry.value}" var="en">
						         {
						         name: '${en.key}',
						         data: ${en.value}
						      },
					      </c:forEach>
					   ];
					   var json = {};
					   json.chart = chart;
					   json.xAxis = xAxis;
					   json.credits = credits;
					   json.title = title;
					   json.series = series;
					   $('#${entry.key}deviationMin').highcharts(json);
					});
					</script>
	</c:forEach>
</div>