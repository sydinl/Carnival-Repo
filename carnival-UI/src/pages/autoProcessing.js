import Head from 'next/head';
import { Box, Container, Stack, Typography, Unstable_Grid2 as Grid } from '@mui/material';
import { Layout as DashboardLayout } from 'src/layouts/dashboard/layout';
import { AuaoMappingDetails } from 'src/sections/automapping/automapping-details';

const Page = () => (
  <>
    <Head>
      <title>
        Auto processing
      </title>
    </Head>
    <Box
      component="main"
      sx={{
        flexGrow: 1,
        py: 8
      }}
    >
      <Container maxWidth="lg">
        <Stack spacing={3}>
          <div>
            <Typography variant="h4">
            Auto processing
            </Typography>
          </div>
          <div>
            <Grid
              container
              spacing={3}
            >
              {/* <Grid
                xs={12}
                md={6}
                lg={4}
              >
                <AccountProfile />
              </Grid> */}
              <Grid
                xs={12}
                md={12}
                lg={12}
              >
                <AuaoMappingDetails />
              </Grid>
            </Grid>
          </div>
        </Stack>
      </Container>
    </Box>
  </>
);

Page.getLayout = (page) => (
  <DashboardLayout>
    {page}
  </DashboardLayout>
);

export default Page;
